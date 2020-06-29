package ibdaa.bloodbank.view.fragment.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.view.fragment.BaseFragment;

import static ibdaa.bloodbank.utils.HelperMethod.replaceFragment;

public class ArticleDetailsFragment extends BaseFragment {

    @BindView(R.id.article_fragment_i_btn_search)
    ImageButton articleFragmentIBtnSearch;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.txt)
    TextView txt;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.article_details_fragment_fav)
    ImageView articleDetailsFragmentFav;

    public ArticleDetailsFragment() {
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
        try {
            intiFragment();
        } catch (Exception e) {
            Toast.makeText(getContext(), "samar " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        View view = inflater.inflate(R.layout.fragment_article_details, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        String articleId = bundle.getString("articleId");
        int postId = Integer.parseInt(articleId);

        String articleTitle = bundle.getString("articleTitle");
        String articleContent = bundle.getString("articleContent");
        String imageUrl = bundle.getString("articleImageUrl");
        String isFav = bundle.getString("articleIdIsFavourite");

        title.setText(articleTitle);
        txt.setText(articleContent);
        Glide.with(this).load(imageUrl).into(image);

        if (isFav.equals("true")) {
            articleDetailsFragmentFav.setImageResource(R.drawable.ic_favorite_black_24dp);
        }else {
            articleDetailsFragmentFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @OnClick(R.id.article_fragment_i_btn_search)
    public void onViewClicked() {
        replaceFragment(getActivity().getSupportFragmentManager(),R.id.home_container, new HomeFragment());
    }
}