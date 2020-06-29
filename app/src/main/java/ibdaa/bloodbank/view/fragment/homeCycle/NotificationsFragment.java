package ibdaa.bloodbank.view.fragment.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ibdaa.bloodbank.R;
import ibdaa.bloodbank.view.fragment.BaseFragment;

public class NotificationsFragment extends BaseFragment {

    public NotificationsFragment() {
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
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }
}
