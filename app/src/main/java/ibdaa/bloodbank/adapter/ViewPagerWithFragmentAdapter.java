package ibdaa.bloodbank.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ibdaa.bloodbank.view.fragment.homeCycle.ArticlesFragment;
import ibdaa.bloodbank.view.fragment.homeCycle.DonationRequestsFragment;

public class ViewPagerWithFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> fragmentsTitle;


    public ViewPagerWithFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        fragments = new ArrayList<>();
        fragmentsTitle = new ArrayList<>();
        fragments.add(new ArticlesFragment());
        fragments.add(new DonationRequestsFragment());
        fragmentsTitle.add("Articles");
        fragmentsTitle.add("Donation Requests");
    }

    public void addPager(Fragment fragments, String fragmentTitle) {
        this.fragments.add(fragments);
        this.fragmentsTitle.add(fragmentTitle);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return fragments.size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitle.get(position);
    }
}
