package ibdaa.bloodbank.view.fragment.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.data.model.settings.Settings;
import ibdaa.bloodbank.view.fragment.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ibdaa.bloodbank.data.api.RetrofitClient.getClient;

public class AboutAppFragment extends BaseFragment {

    @BindView(R.id.about_app_tv_txt)
    TextView aboutAppTvTxt;

    public AboutAppFragment() {
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
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        ButterKnife.bind(this, view);

        getAboutAppTxt();

        return view;
    }

    private void getAboutAppTxt() {
        Call<Settings> call = getClient().getAppSettings("Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27");
        startCall(call);
    }

    private void startCall(Call<Settings> call) {
        call.enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        aboutAppTvTxt.setText(response.body().getData().getAboutApp());
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
