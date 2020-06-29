package ibdaa.bloodbank.view.fragment.splashCycle;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.adapter.SlideAdapter;
import ibdaa.bloodbank.view.fragment.BaseFragment;
import ibdaa.bloodbank.view.fragment.userCycle.UserLoginFragment;

import static ibdaa.bloodbank.utils.HelperMethod.replaceFragment;

public class OnBoardingFragment extends BaseFragment {

    private static final int MAX_STEP = 3;

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.img_first)
    ImageView imgFirst;
    @BindView(R.id.layout_dots)
    LinearLayout layoutDots;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intiFragment();
        View view = inflater.inflate(R.layout.fragment_on_boarding, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bottomProgressDots(0);
        viewPager.setAdapter(new SlideAdapter(getContext()));
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    @Override
    public void onBack() {
        baseActivity.finish();
    }

    @OnClick({R.id.img_first})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_first:
                int current = viewPager.getCurrentItem() + 1;
                if (current < MAX_STEP) {
                    viewPager.setCurrentItem(current);
                } else {
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new UserLoginFragment());
                }
        }
    }

    private void bottomProgressDots(int current_index) {
        ImageView[] dots = new ImageView[MAX_STEP];
        layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(getContext());
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(40, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(R.color.background, PorterDuff.Mode.SRC_IN);
            layoutDots.addView(dots[i]);
        }

        if (dots.length > 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(20, 15));
            params.setMargins(10, 10, 10, 10);
            dots[current_index].setLayoutParams(params);

            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
}