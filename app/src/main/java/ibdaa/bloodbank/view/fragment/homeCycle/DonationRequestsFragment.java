package ibdaa.bloodbank.view.fragment.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.adapter.EmptySpinnerAdapter;
import ibdaa.bloodbank.adapter.DonationAdapter;
import ibdaa.bloodbank.data.model.donations.Donations;
import ibdaa.bloodbank.data.model.donations.DonationsData;
import ibdaa.bloodbank.utils.OnEndLess;
import ibdaa.bloodbank.view.fragment.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ibdaa.bloodbank.data.api.RetrofitClient.getClient;
import static ibdaa.bloodbank.utils.GeneralRequest.getNewSpinnerData;
import static ibdaa.bloodbank.utils.GeneralRequest.getSpinnerData;
import static ibdaa.bloodbank.utils.HelperMethod.replaceFragment;

public class DonationRequestsFragment extends BaseFragment {

    @BindView(R.id.donation_request_fragment_spinner_blood_type)
    Spinner donationRequestFragmentSpinnerBloodType;
    @BindView(R.id.donation_request_fragment_spinner_government)
    Spinner donationRequestFragmentSpinnerGovernment;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.donation_swipe_refresh_layout)
    SwipeRefreshLayout donationSwipeRefreshLayout;
    private EmptySpinnerAdapter bloodTypesAdapter;
    private EmptySpinnerAdapter governmentsAdapter;
    private LinearLayoutManager linearLayout;
    private DividerItemDecoration dividerItemDecoration;
    private List<DonationsData> donationsDataList = new ArrayList<>();
    private DonationAdapter donationAdapter;
    private Integer maxPage = 0;
    private OnEndLess onEndLess;
    private boolean filter = false;
    private EmptySpinnerAdapter citiesAdapter;

    public DonationRequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intiFragment();
        View view = inflater.inflate(R.layout.fragment_donation_requests, container, false);
        unbinder = ButterKnife.bind(this, view);

        bloodTypesAdapter = new EmptySpinnerAdapter(getActivity());
        governmentsAdapter = new EmptySpinnerAdapter(getActivity());

        getNewSpinnerData(getActivity(), donationRequestFragmentSpinnerBloodType, bloodTypesAdapter,
                getClient().getBlood_types(), "select", 0);

        getNewSpinnerData(getActivity(), donationRequestFragmentSpinnerGovernment, governmentsAdapter,
                getClient().getGvernorates(), "select", 0);

        init();

        donationSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDonations(1);
            }
        });

        return view;
    }

    private void init() {
        linearLayout = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayout);
        dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        if (filter) {
                            //onFilter(current_page);
                        } else {
                            getDonations(current_page);
                        }
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };

        recyclerView.addOnScrollListener(onEndLess);

        donationAdapter = new DonationAdapter(getActivity(), donationsDataList);
        recyclerView.setAdapter(donationAdapter);

        getDonations(1);
    }

    private void getDonations(int page) {
        Call<Donations> call = getClient().donationRequests("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl", page);
        startCall(call, page);
    }

    @OnClick({R.id.add_request_fab,R.id.donation_request_fragment_i_btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_request_fab:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_container, new SendDonationRequestFragment());
                break;
            case R.id.donation_request_fragment_i_btn_search:
                onFilter(1);
                break;

        }
    }

    private void onFilter(int page) {
        filter = true;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        onEndLess.previousTotal = 0;

        donationsDataList = new ArrayList<>();
        donationAdapter = new DonationAdapter(getActivity(), donationsDataList);
        recyclerView.setAdapter(donationAdapter);

        Call<Donations> call = getClient().donationRequests("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl",
                page, bloodTypesAdapter.selectedId, governmentsAdapter.selectedId);
        startCall(call, page);
    }

    private void startCall(Call<Donations> call, int page) {
        call.enqueue(new Callback<Donations>() {
            @Override
            public void onResponse(Call<Donations> call, Response<Donations> response) {
                try {
                    donationSwipeRefreshLayout.setRefreshing(false);
                    if (response.body().getStatus() == 1) {
                        if (page == 1) {
                            onEndLess.previousTotal = 0;
                            onEndLess.previous_page = 1;
                            onEndLess.current_page = 1;

                            donationsDataList = new ArrayList<>();
                            donationAdapter = new DonationAdapter(getActivity(), donationsDataList);
                            recyclerView.setAdapter(donationAdapter);
                        }
                        maxPage = response.body().getData().getLastPage();
                        donationsDataList.addAll(response.body().getData().getData());
                        donationAdapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Donations> call, Throwable t) {

            }
        });
    }
}
