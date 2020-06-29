package ibdaa.bloodbank.view.fragment.homeCycle;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.adapter.EmptySpinnerAdapter;
import ibdaa.bloodbank.adapter.ArticleAdapter;
import ibdaa.bloodbank.data.model.posts.Posts;
import ibdaa.bloodbank.data.model.posts.PostsData;
import ibdaa.bloodbank.utils.OnEndLess;
import ibdaa.bloodbank.view.fragment.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ibdaa.bloodbank.data.api.RetrofitClient.getClient;
import static ibdaa.bloodbank.utils.GeneralRequest.getSpinnerData;

public class ArticlesFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.spinner_category)
    Spinner spinnerCategory;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.articles_swipe_refresh_layout)
    SwipeRefreshLayout articlesSwipeRefreshLayout;
    @BindView(R.id.article_fragment_i_btn_search)
    ImageButton articleFragmentIBtnSearch;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private OnEndLess onEndLess;
    private int maxPage;
    private boolean filter;
    private ArticleAdapter articleAdapter;
    private List<PostsData> postsList = new ArrayList<>();
    EmptySpinnerAdapter categorySpinnerAdapter;

    public ArticlesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intiFragment();
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        ButterKnife.bind(this, view);

        categorySpinnerAdapter = new EmptySpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), spinnerCategory, categorySpinnerAdapter, getClient().getCategories(), getString(R.string.select_category), categorySpinnerAdapter.selectedId);

        init();

        articlesSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getArticles(1);
            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerCategory.getSelectedItemId() > 0 || edSearch.getText().toString().length() > 0) {
                    getPostsWithFilter();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        return view;
    }

    private void filter(String text) {
        ArrayList<PostsData> postsDataList = new ArrayList<>();
        for (PostsData item : postsList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                postsDataList.add(item);
            }
        }
        articleAdapter.filterList(postsDataList);
    }

    private void getPostsWithFilter() {

        getClient().getFilteredPosts("Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27", 1, edSearch.getText().toString(),
                categorySpinnerAdapter.selectedId).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                try {
                    if (response.body().getStatus() == 1)
                        postsList = response.body().getData().getData();
                    {
                        articleAdapter = new ArticleAdapter(getActivity(), (List<PostsData>) postsList);
                        recyclerView.setAdapter(articleAdapter);
                        articleAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                            //onFilter(current_page);
                        } else {
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
        articleAdapter = new ArticleAdapter(getActivity(), postsList);
        recyclerView.setAdapter(articleAdapter);
        getArticles(1);
    }

    private void getArticles(int page) {
        Call<Posts> call = getClient().getAllPosts("Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27", page);
        startCall(call, page);
    }

    private void startCall(Call<Posts> call, int page) {
        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                try {
                    articlesSwipeRefreshLayout.setRefreshing(false);
                    if (response.body().getStatus() == 1) {
                        if (page == 1) {
                            onEndLess.previousTotal = 0;
                            onEndLess.previous_page = 1;
                            onEndLess.current_page = 1;

                            postsList = new ArrayList<>();
                            articleAdapter = new ArticleAdapter(getActivity(), postsList);
                            recyclerView.setAdapter(articleAdapter);
                        }
                        maxPage = response.body().getData().getLastPage();
                        postsList.addAll(response.body().getData().getData());
                        articleAdapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onFilter(int page) {
        filter = true;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        onEndLess.previousTotal = 0;

        postsList = new ArrayList<>();
        articleAdapter = new ArticleAdapter(getActivity(), postsList);
        recyclerView.setAdapter(articleAdapter);

        Call<Posts> call = getClient().getFilteredPosts("Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27",
                page,edSearch.getText().toString(),categorySpinnerAdapter.selectedId);
        startCall(call, page);
    }

    @OnClick(R.id.article_fragment_i_btn_search)
    public void onViewClicked() {
        onFilter(1);
    }
}