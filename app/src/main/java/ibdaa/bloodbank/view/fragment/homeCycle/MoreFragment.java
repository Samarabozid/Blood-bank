package ibdaa.bloodbank.view.fragment.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.view.fragment.BaseFragment;
import ibdaa.bloodbank.view.fragment.userCycle.UserLoginFragment;

import static ibdaa.bloodbank.utils.HelperMethod.replaceFragment;

public class MoreFragment extends BaseFragment {

    @BindView(R.id.more_fragment_favourites)
    TextView moreFragmentFavourites;
    @BindView(R.id.more_fragment_contact_us)
    TextView moreFragmentContactUs;
    @BindView(R.id.more_fragment_about_app)
    TextView moreFragmentAboutApp;
    @BindView(R.id.more_fragment_rating_app)
    TextView moreFragmentRatingApp;
    @BindView(R.id.more_fragment_settings)
    TextView moreFragmentSettings;
    @BindView(R.id.more_fragment_logout)
    TextView moreFragmentLogout;

    public MoreFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }

    @OnClick({R.id.more_fragment_favourites, R.id.more_fragment_contact_us, R.id.more_fragment_about_app, R.id.more_fragment_rating_app, R.id.more_fragment_settings, R.id.more_fragment_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more_fragment_favourites:
                replaceFragment(getActivity().getSupportFragmentManager(),R.id.home_container, new FavouritesFragment());
                break;
            case R.id.more_fragment_contact_us:
                break;
            case R.id.more_fragment_about_app:
                replaceFragment(getActivity().getSupportFragmentManager(),R.id.home_container, new AboutAppFragment());
                break;
            case R.id.more_fragment_rating_app:
                break;
            case R.id.more_fragment_settings:
                replaceFragment(getActivity().getSupportFragmentManager(),R.id.home_container, new SettingsFragment());
                break;
            case R.id.more_fragment_logout:
                replaceFragment(getActivity().getSupportFragmentManager(),R.id.home_container, new UserLoginFragment());
                break;
        }
    }
}
