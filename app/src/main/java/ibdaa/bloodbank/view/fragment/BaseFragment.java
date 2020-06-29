package ibdaa.bloodbank.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ibdaa.bloodbank.R;
import ibdaa.bloodbank.view.activity.BaseActivity;

public class BaseFragment extends Fragment {
    public BaseActivity baseActivity;

    public void intiFragment() {
        baseActivity = (BaseActivity) getActivity();
        baseActivity.baseFragment = this;
    }

    public void onBack() {
        baseActivity.superBackPressed();
    }
}
