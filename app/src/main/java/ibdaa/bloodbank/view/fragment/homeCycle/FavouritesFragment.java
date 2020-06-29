package ibdaa.bloodbank.view.fragment.homeCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.adapter.FavouritesAdapter;
import ibdaa.bloodbank.data.model.favorites.Favorites;
import ibdaa.bloodbank.data.model.favorites.FavoritesData;
import ibdaa.bloodbank.utils.OnEndLess;
import ibdaa.bloodbank.view.fragment.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ibdaa.bloodbank.data.api.RetrofitClient.getClient;

public class FavouritesFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private OnEndLess onEndLess;
    private int maxPage;
    private boolean filter;
    private FavouritesAdapter testFavouritesAdapter;
    private List<FavoritesData> favouritePostsList = new ArrayList<>();

    public FavouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intiFragment();
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        if (filter) {
                            onFilter(current_page);
                        }else {
                            getArticles(current_page);
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

        testFavouritesAdapter = new FavouritesAdapter(getActivity(),favouritePostsList);
        recyclerView.setAdapter(testFavouritesAdapter);

        getArticles(1);
    }

    private void getArticles(int page) {
        Call<Favorites> call = getClient().getAllFavourites("Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27", page);
        startCall(call, page);
    }

    private void startCall(Call<Favorites> call, int page) {
        call.enqueue(new Callback<Favorites>() {
            @Override
            public void onResponse(Call<Favorites> call, Response<Favorites> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        favouritePostsList.addAll(response.body().getData().getData());
                        Log.d("eee",response.body().getData().getData().get(1).getTitle());
                        testFavouritesAdapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Favorites> call, Throwable t) {

            }
        });
    }

    private void onFilter(int page) {
        filter = true;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        onEndLess.previousTotal = 0;

        favouritePostsList = new ArrayList<>();
        testFavouritesAdapter = new FavouritesAdapter(getActivity(), favouritePostsList);
        recyclerView.setAdapter(testFavouritesAdapter);

        Call<Favorites> call = getClient().getAllFavourites("Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27",
                page);
        startCall(call, page);
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}