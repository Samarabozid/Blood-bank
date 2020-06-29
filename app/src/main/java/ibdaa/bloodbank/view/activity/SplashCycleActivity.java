package ibdaa.bloodbank.view.activity;

import android.os.Bundle;

import ibdaa.bloodbank.R;
import ibdaa.bloodbank.view.fragment.splashCycle.SplashScreenFragment;

import static ibdaa.bloodbank.utils.HelperMethod.replaceFragment;

public class SplashCycleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_cycle);

        replaceFragment(getSupportFragmentManager(),R.id.container, new SplashScreenFragment());

    }
}
