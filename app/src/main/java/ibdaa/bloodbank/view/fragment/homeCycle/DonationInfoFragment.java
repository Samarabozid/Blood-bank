package ibdaa.bloodbank.view.fragment.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.data.model.settings.Settings;
import ibdaa.bloodbank.view.fragment.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ibdaa.bloodbank.data.api.RetrofitClient.getClient;

public class DonationInfoFragment extends BaseFragment {

    @BindView(R.id.article_fragment_i_btn_search)
    ImageButton articleFragmentIBtnSearch;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.txt)
    TextView txt;

    public DonationInfoFragment() {
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
        View view = inflater.inflate(R.layout.fragment_donation_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @OnClick(R.id.article_fragment_i_btn_search)
    public void onViewClicked() {
    }
}
