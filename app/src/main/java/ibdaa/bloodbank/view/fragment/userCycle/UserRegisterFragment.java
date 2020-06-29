package ibdaa.bloodbank.view.fragment.userCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.utils.DateTxt;
import ibdaa.bloodbank.adapter.EmptySpinnerAdapter;
import ibdaa.bloodbank.data.model.login.Login;
import ibdaa.bloodbank.utils.HelperMethod;
import ibdaa.bloodbank.view.fragment.BaseFragment;
import retrofit2.Call;

import static ibdaa.bloodbank.data.api.RetrofitClient.getClient;
import static ibdaa.bloodbank.utils.GeneralRequest.getNewSpinnerData;
import static ibdaa.bloodbank.utils.GeneralRequest.userData;
import static ibdaa.bloodbank.utils.HelperMethod.showCalender;

public class UserRegisterFragment extends BaseFragment {

    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.tv_birth_date)
    TextView tvBirthDate;
    @BindView(R.id.tv_blood_type)
    TextView tvBloodType;
    @BindView(R.id.spinner_blood_type)
    Spinner spinnerBloodType;
    @BindView(R.id.tv_last_donation)
    TextView tvLastDonation;
    @BindView(R.id.tv_governrate)
    TextView tvCountry;
    @BindView(R.id.spinner_governrate)
    Spinner spinnerGovernrate;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.spinner_city)
    Spinner spinnerCity;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_confirm_password)
    EditText edtConfirmPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private EmptySpinnerAdapter bloodTypesAdapter, citiesAdapter, governmentsAdapter;
    private DateTxt birthdayDate, lastDonationDate;
    private Unbinder unbinder;
    private String name, email, phone, password, confirmPassword, lastDonation, birthDate;
    private int cityId, bloodTypeId;

    public UserRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_register, container, false);
        unbinder = ButterKnife.bind(this, view);

        setDates();
        setSpinnerData();
        return view;
    }

    private void setDates() {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        Calendar calendar = Calendar.getInstance();
        String day = decimalFormat.format(Double.valueOf(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))));
        String month = decimalFormat.format(Double.valueOf(String.valueOf(calendar.get(Calendar.MONTH + 1))));
        String year = String.valueOf(calendar.get(Calendar.YEAR));

        lastDonationDate = new DateTxt(day, month, year, day + "/" + month + "/" + year);
        birthdayDate = new DateTxt("01", "01", "1997", "01/01/1997");
    }

    public void setSpinnerData() {

        bloodTypesAdapter = new EmptySpinnerAdapter(getActivity());
        governmentsAdapter = new EmptySpinnerAdapter(getActivity());
        citiesAdapter = new EmptySpinnerAdapter(getActivity());

        getNewSpinnerData(getActivity(), spinnerBloodType, bloodTypesAdapter,
                getClient().getBlood_types(), "select", 0);

        getNewSpinnerData(getActivity(), spinnerGovernrate, governmentsAdapter,
                getClient().getGvernorates(), "select", 0, new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        getNewSpinnerData(getActivity(), spinnerCity, citiesAdapter,
                                getClient().getCity(governmentsAdapter.selectedId), "select", 0);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_birth_date, R.id.tv_last_donation, R.id.btn_register})
    public void onViewClicked(View view) {
        HelperMethod.disappearKeypad(getActivity(), view);
        switch (view.getId()) {
            case R.id.tv_birth_date:
                showCalender(getActivity(), getString(R.string.select_date), tvBirthDate, birthdayDate);
                break;
            case R.id.tv_last_donation:
                showCalender(getActivity(), getString(R.string.select_date), tvLastDonation, lastDonationDate);
                break;
            case R.id.btn_register:
                addUser();
                break;
        }
    }

    private void addUser() {
        name = edtName.getText().toString();
        email = edtEmail.getText().toString();
        phone = edtPhone.getText().toString();
        password = edtPassword.getText().toString();
        confirmPassword = edtConfirmPassword.getText().toString();
        lastDonation = tvLastDonation.getText().toString();
        birthDate = tvBirthDate.getText().toString();
        cityId = citiesAdapter.selectedId;
        bloodTypeId = bloodTypesAdapter.selectedId;

        Call<Login> call = getClient().signUp(name, email, birthDate, cityId, phone, lastDonation,
                password, confirmPassword, bloodTypeId);
        userData(getActivity(), call, password, true, true);
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }
}