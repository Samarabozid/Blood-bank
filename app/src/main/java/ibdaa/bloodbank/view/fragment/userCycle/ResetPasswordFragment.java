package ibdaa.bloodbank.view.fragment.userCycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.data.model.forgetPassword.ForgetPassword;
import ibdaa.bloodbank.view.activity.EmptyHomeActivity;
import ibdaa.bloodbank.view.fragment.BaseFragment;
import ibdaa.bloodbank.view.fragment.homeCycle.HomePageFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static ibdaa.bloodbank.data.api.RetrofitClient.getClient;
import static ibdaa.bloodbank.utils.HelperMethod.replaceFragment;
import static ibdaa.bloodbank.utils.InternetState.isConnected;

public class ResetPasswordFragment extends BaseFragment {

    @BindView(R.id.edt_verification_code)
    EditText edtVerificationCode;
    @BindView(R.id.edt_new_password)
    EditText edtNewPassword;
    @BindView(R.id.edt_confirm_new_password)
    EditText edtConfirmNewPassword;
    @BindView(R.id.btn_reset_password)
    Button btnChange;

    Unbinder unbinder;
    public String phone;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intiFragment();
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @OnClick(R.id.btn_reset_password)
    public void onViewClicked() {
        onCall(phone);
        //replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new UserRegisterFragment());
    }

    private void onCall(String phone) {
        if (isConnected(getActivity())) {
            getClient().newPassword(edtNewPassword.getText().toString(),edtConfirmNewPassword.getText().toString(),
                    edtVerificationCode.getText().toString(),phone).enqueue(new Callback<ForgetPassword>() {
                @Override
                public void onResponse(Call<ForgetPassword> call, Response<ForgetPassword> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            Intent i = new Intent(getActivity(), EmptyHomeActivity.class);
                            startActivity(i);
                            //replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_container, new HomePageFragment());
                        } else {
                            Toast.makeText(baseActivity, "wrong", Toast.LENGTH_SHORT).show();
                        }

                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<ForgetPassword> call, Throwable t) {
                    Log.d(TAG, "onFailure" + t.toString());
                }
            });
        }
    }
}
