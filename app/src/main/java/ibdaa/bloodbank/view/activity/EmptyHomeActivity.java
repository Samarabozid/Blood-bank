package ibdaa.bloodbank.view.activity;

import android.os.Bundle;

import ibdaa.bloodbank.R;
import ibdaa.bloodbank.view.fragment.homeCycle.HomePageFragment;

import static ibdaa.bloodbank.utils.HelperMethod.replaceFragment;

public class EmptyHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_home);

        replaceFragment(getSupportFragmentManager(),R.id.home_container, new HomePageFragment());
    }
}
