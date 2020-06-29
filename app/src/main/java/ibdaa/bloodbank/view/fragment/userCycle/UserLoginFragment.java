package ibdaa.bloodbank.view.fragment.userCycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.data.api.RetrofitClient;
import ibdaa.bloodbank.data.model.login.Login;
import ibdaa.bloodbank.view.activity.EmptyHomeActivity;
import ibdaa.bloodbank.view.fragment.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ibdaa.bloodbank.data.api.RetrofitClient.getClient;
import static ibdaa.bloodbank.utils.GeneralRequest.userData;
import static ibdaa.bloodbank.utils.HelperMethod.replaceFragment;

public class UserLoginFragment extends BaseFragment {

    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.forgotpassword_tv)
    TextView forgotpasswordTv;
    @BindView(R.id.ch_remmember)
    CheckBox chRemmember;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_signup)
    TextView tvSignup;

    String phone, password;

    public UserLoginFragment() {
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
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }

    @OnClick({R.id.forgotpassword_tv, R.id.btn_login, R.id.tv_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forgotpassword_tv:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new ForgetPasswordFragment());
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_signup:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new UserRegisterFragment());
                break;
        }
    }

    private void login() {
        phone = edtPhone.getText().toString();
        password = edtPassword.getText().toString();
        boolean remember = chRemmember.isChecked();

        Call<Login> call = getClient().login(phone,password);
        userData(getActivity(),call,password,remember,true);

        /*phone = edtPhone.getText().toString();
        password = edtPassword.getText().toString();
        Call<Login> call = RetrofitClient.getClient().login(phone, password);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Intent i = new Intent(getActivity(), EmptyHomeActivity.class);
                        startActivity(i);
                    }
                    //Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });*/
    }
}