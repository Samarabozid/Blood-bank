package ibdaa.bloodbank.view.fragment.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.adapter.EmptySpinnerAdapter;
import ibdaa.bloodbank.data.model.login.Client;
import ibdaa.bloodbank.data.model.login.Login;
import ibdaa.bloodbank.view.fragment.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ibdaa.bloodbank.data.api.RetrofitClient.getClient;
import static ibdaa.bloodbank.utils.GeneralRequest.getSpinnerData;
import static ibdaa.bloodbank.utils.HelperMethod.showCalender;

public class ProfileFragment extends BaseFragment {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_user_name)
    EditText edtUserName;
    @BindView(R.id.edt_user_email)
    EditText edtUserEmail;
    @BindView(R.id.spinner_user_blood_type)
    Spinner spinnerUserBloodType;
    @BindView(R.id.spinner_user_governorate)
    Spinner spinnerUserGovernorate;
    @BindView(R.id.spinner_user_city)
    Spinner spinnerUserCity;
    @BindView(R.id.edt_user_phone)
    EditText edtUserPhone;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_confirm_password)
    EditText edtConfirmPassword;
    @BindView(R.id.btn_edit_profile)
    Button btnEditProfile;
    @BindView(R.id.tv_user_birth_day)
    TextView tvUserBirthDay;
    @BindView(R.id.tv_user_last_donation)
    TextView tvUserLastDonation;
    private EmptySpinnerAdapter spinnerUserBloodTypeAdapter;
    private EmptySpinnerAdapter spinnerUserGovernratesAdapter;
    private EmptySpinnerAdapter spinnerUserCitysAdapter;
    Unbinder unbinder;
    private int bloodTypeSelectedId, citiesSelectedId, governmentSelectedId;

    public ProfileFragment() {
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        getProfile();
        //setUserData();
        return view;
    }

    private void getProfile() {
        getClient().getprofile("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl").enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        setData(response.body().getData().getClient());
                        spinnerUserGovernratesAdapter = new EmptySpinnerAdapter(getActivity());
                        spinnerUserBloodTypeAdapter = new EmptySpinnerAdapter(getActivity());
                        spinnerUserCitysAdapter = new EmptySpinnerAdapter(getActivity());

                        spinnerUserBloodTypeAdapter = new EmptySpinnerAdapter(getActivity());
                        getSpinnerData(getActivity(), spinnerUserBloodType, spinnerUserBloodTypeAdapter,getClient().getBlood_types() ,getString(R.string.bloodtype),bloodTypeSelectedId);

                        spinnerUserGovernratesAdapter = new EmptySpinnerAdapter(getActivity());
                        spinnerUserCitysAdapter = new EmptySpinnerAdapter(getActivity());
                        getSpinnerData(getActivity(),spinnerUserGovernorate,spinnerUserGovernratesAdapter,getClient().getGvernorates(),getString(R.string.governrate),spinnerUserGovernratesAdapter.selectedId,
                                spinnerUserCity,spinnerUserCitysAdapter,getClient().getCity(spinnerUserGovernratesAdapter.selectedId),getString(R.string.city),citiesSelectedId);


                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }

    /*private void setUserData() {
        bloodTypeSelectedId = registerData.getClient().getBloodType().getId();
        governmentSelectedId = registerData.getClient().getCity().getGovernorate().getId();
        citiesSelectedId = registerData.getClient().getCity().getId();

        edtUserName.setText(registerData.getClient().getName());
        edtUserEmail.setText(registerData.getClient().getEmail());
        edtUserPhone.setText(registerData.getClient().getPhone());
        edtPassword.setText(LoadData(getActivity(), USER_PASSWORD));
        edtConfirmPassword.setText(LoadData(getActivity(), USER_PASSWORD));

        tvUserBirthDay.setText(registerData.getClient().getBirthDate());
        tvUserLastDonation.setText(registerData.getClient().getDonationLastDate());

    }*/

    private void setData(Client client) {

    }

    @Override
    public void onBack() {
        getActivity().finish();
    }

    @OnClick({R.id.tv_user_last_donation, R.id.tv_user_birth_day, R.id.btn_edit_profile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_user_birth_day:
                //showCalender(getActivity(), getString(R.string.select_date), tvUserBirthDay, birthdayDate);
                break;
            case R.id.tv_user_last_donation:
                //showCalender(getActivity(), getString(R.string.select_date), tvUserLastDonation, lastDonationDate);
                break;
            case R.id.btn_edit_profile:
                break;
        }
    }
}