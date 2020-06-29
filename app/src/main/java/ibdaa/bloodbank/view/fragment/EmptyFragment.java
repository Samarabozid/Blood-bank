package ibdaa.bloodbank.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ibdaa.bloodbank.R;

public class EmptyFragment extends BaseFragment {

    public EmptyFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_empty, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
