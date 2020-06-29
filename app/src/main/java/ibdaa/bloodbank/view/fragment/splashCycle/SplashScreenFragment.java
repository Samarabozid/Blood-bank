package ibdaa.bloodbank.view.fragment.splashCycle;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ibdaa.bloodbank.R;
import ibdaa.bloodbank.view.fragment.BaseFragment;
import ibdaa.bloodbank.view.fragment.userCycle.UserLoginFragment;

import static ibdaa.bloodbank.utils.HelperMethod.replaceFragment;

public class SplashScreenFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        new Handler().postDelayed(() -> {
            //Intent i = new Intent(getActivity(), EmptyHomeActivity.class);
            //startActivity(i);
            //UserRegisterFragment userRegisterFragment= new UserRegisterFragment();
            //HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(),R.id.container, userRegisterFragment);
            //UserLoginFragment userLoginFragment= new UserLoginFragment();
            replaceFragment(getActivity().getSupportFragmentManager(),R.id.container, new UserLoginFragment());
            //replaceFragment(getActivity().getSupportFragmentManager(),R.id.container,new OnBoardingFragment());
        }, 3000);

    }

    @Override
    public void onBack() {
        baseActivity.finish();
    }
}
