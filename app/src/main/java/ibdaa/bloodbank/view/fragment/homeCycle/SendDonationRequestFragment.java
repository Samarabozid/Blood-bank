package ibdaa.bloodbank.view.fragment.homeCycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.adapter.EmptySpinnerAdapter;
import ibdaa.bloodbank.data.model.createDonationRequest.Requests;
import ibdaa.bloodbank.view.activity.MapsActivity;
import ibdaa.bloodbank.view.fragment.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ibdaa.bloodbank.data.api.RetrofitClient.getClient;
import static ibdaa.bloodbank.utils.GeneralRequest.getNewSpinnerData;
import static ibdaa.bloodbank.utils.GeneralRequest.getSpinnerData;
import static ibdaa.bloodbank.utils.HelperMethod.replaceFragment;

public class SendDonationRequestFragment extends BaseFragment {

    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_age)
    EditText edtAge;
    @BindView(R.id.spinner_blood_type)
    Spinner spinnerBloodType;
    @BindView(R.id.edt_no_akyas)
    EditText edtNoAkyas;
    @BindView(R.id.edt_hospital_name)
    TextView edtHospitalName;
    @BindView(R.id.tv_hospital_address)
    TextView tvHospitalAddress;
    @BindView(R.id.spinner_city)
    Spinner spinnerCity;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_notes)
    EditText edtNotes;
    @BindView(R.id.btn_send_donation_request)
    Button btnSendDonationRequest;
    private EmptySpinnerAdapter bloodTypesAdapter;
    private EmptySpinnerAdapter citisAdapter;
    private EmptySpinnerAdapter governmentsAdapter;

    private String patientName,hospitalName,hospitalAddress,phone,notes;
    private int bloodTypeId,patientAge,bagsNum,cityId;
    private double latitude,longitude;

    public SendDonationRequestFragment() {
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
        View view = inflater.inflate(R.layout.fragment_send_donation_request, container, false);
        ButterKnife.bind(this, view);

        bloodTypesAdapter = new EmptySpinnerAdapter(getActivity());
        governmentsAdapter = new EmptySpinnerAdapter(getActivity());

        getNewSpinnerData(getActivity(), spinnerBloodType, bloodTypesAdapter,
                getClient().getBlood_types(), "select", 0);

        getNewSpinnerData(getActivity(), spinnerCity, governmentsAdapter,
                getClient().getGvernorates(), "select", 0);

        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @OnClick({R.id.tv_hospital_address, R.id.btn_send_donation_request})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_hospital_address:
                Intent i = new Intent(getActivity(), MapsActivity.class);
                startActivity(i);
                break;
            case R.id.btn_send_donation_request:
                createDonationRequest();
                //replaceFragment(getActivity().getSupportFragmentManager(),R.id.home_container, new HomePageFragment());
                break;
        }
    }

    private void createDonationRequest() {
        Call<Requests> call = getClient().createDonationRequest("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl",
                patientName,patientAge,bloodTypeId,bagsNum,hospitalName,hospitalAddress,cityId,phone,
                notes,latitude,longitude);
        startCall(call);
    }

    private void startCall(Call<Requests> call) {
        patientName = edtName.getText().toString();
        String age = edtAge.getText().toString();
        patientAge = Integer.parseInt(age);
        bloodTypeId = bloodTypesAdapter.selectedId;
        String bags = edtNoAkyas.getText().toString();
        bagsNum = Integer.parseInt(bags);
        hospitalName = edtHospitalName.getText().toString();
        hospitalAddress = tvHospitalAddress.toString();
        cityId = governmentsAdapter.selectedId;
        phone = edtPhone.getText().toString();
        notes = edtNotes.getText().toString();

        call.enqueue(new Callback<Requests>() {
            @Override
            public void onResponse(Call<Requests> call, Response<Requests> response) {
                try{
                    if (response.body().getStatus() == 1) {
                        replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new HomeFragment());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<Requests> call, Throwable t) {

            }
        });
    }
}