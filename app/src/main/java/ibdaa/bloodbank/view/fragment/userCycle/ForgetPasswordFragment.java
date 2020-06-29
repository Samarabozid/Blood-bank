package ibdaa.bloodbank.view.fragment.userCycle;

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
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.data.model.forgetPassword.ForgetPassword;
import ibdaa.bloodbank.view.fragment.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static ibdaa.bloodbank.data.api.RetrofitClient.getClient;
import static ibdaa.bloodbank.utils.HelperMethod.replaceFragment;
import static ibdaa.bloodbank.utils.InternetState.isConnected;

public class ForgetPasswordFragment extends BaseFragment {

    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.btn_send_verification_code)
    Button btnSend;

    public ForgetPasswordFragment() {
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
        View view  = inflater.inflate(R.layout.fragment_forget_password, container, false);;
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @OnClick(R.id.btn_send_verification_code)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case  R.id.btn_send_verification_code:
                String phone = edtPhone.getText().toString();
                if(phone.length() == 11)
                {
                    onCall(phone);
                }else {
                    Toast.makeText(baseActivity, "Enter Right Phone", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void onCall(String phone) {
        if (isConnected(getActivity())) {
            getClient().onForgetPassword(phone).enqueue(new Callback<ForgetPassword>() {
                @Override
                public void onResponse(Call<ForgetPassword> call, Response<ForgetPassword> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
                            resetPasswordFragment.phone = phone;
                            replaceFragment(getActivity().getSupportFragmentManager(), R.id.container,resetPasswordFragment);
                        }else {
                            Toast.makeText(baseActivity, "wrong", Toast.LENGTH_SHORT).show();
                        }

                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<ForgetPassword> call, Throwable t) {
                    Log.d(TAG,"onFailure" + t.toString());
                }
            });
        }
    }
}