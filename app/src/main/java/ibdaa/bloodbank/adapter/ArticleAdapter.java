package ibdaa.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ibdaa.bloodbank.R;
import ibdaa.bloodbank.data.model.posts.PostsData;
import ibdaa.bloodbank.view.activity.BaseActivity;
import ibdaa.bloodbank.view.activity.EmptyHomeActivity;
import ibdaa.bloodbank.view.fragment.homeCycle.ArticleDetailsFragment;

import static ibdaa.bloodbank.data.api.RetrofitClient.getClient;
import static ibdaa.bloodbank.utils.HelperMethod.replaceFragment;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleRowHolder> {
    private static final String API_TOKEN = "Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27";
    Context context;
    private List<PostsData> postsList = new ArrayList<>();
    private BaseActivity activity;

    public ArticleAdapter(Activity activity, List<PostsData> postsList) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.postsList = postsList;
    }

    @NonNull
    @Override
    public ArticleAdapter.ArticleRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, null);
        ArticleRowHolder articleRowHolder = new ArticleRowHolder(view);
        return articleRowHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ArticleRowHolder holder, final int position) {

        holder.postBackgroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmptyHomeActivity emptyHomeActivity = (EmptyHomeActivity) context;
                ArticleDetailsFragment articleDetailsFragment = new ArticleDetailsFragment();
                String articleId = postsList.get(position).getId().toString();
                String articleTitle = postsList.get(position).getTitle();
                String articleContent = postsList.get(position).getContent();
                String articleImageUrl = postsList.get(position).getThumbnailFullPath();
                Boolean articleIsFav = postsList.get(position).getIsFavourite();
                String articleIdIsFavourite = String.valueOf(articleIsFav);

                Bundle bundle = new Bundle();
                bundle.putString("articleId",articleId);
                bundle.putString("articleTitle",articleTitle);
                bundle.putString("articleContent",articleContent);
                bundle.putString("articleImageUrl",articleImageUrl);
                bundle.putString("articleIdIsFavourite",articleIdIsFavourite);

                articleDetailsFragment.setArguments(bundle);

                replaceFragment(emptyHomeActivity.getSupportFragmentManager(),R.id.home_container, articleDetailsFragment);
            }
        });

        Glide.with(context).load(postsList.get(position).getThumbnailFullPath())
                .into(holder.postBackgroundImage);
        holder.tvTitleOfPost.setText(postsList.get(position).getTitle());
        if (postsList.get(position).getIsFavourite()) {
            holder.imBuIsFav.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            holder.imBuIsFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

        holder.imBuIsFav.setOnClickListener(v -> getClient().addAndRemoveFavourite(postsList.get(position).getId(), API_TOKEN));
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public void filterList(ArrayList<PostsData> filteredList) {
        postsList = filteredList;
        notifyDataSetChanged();
    }

    public class ArticleRowHolder extends RecyclerView.ViewHolder {
        ImageView postBackgroundImage, imBuIsFav;
        TextView tvTitleOfPost;

        public ArticleRowHolder(@NonNull View itemView) {
            super(itemView);
            postBackgroundImage = itemView.findViewById(R.id.post_image_view);
            tvTitleOfPost = itemView.findViewById(R.id.post_txt_view);
            imBuIsFav = itemView.findViewById(R.id.post_fav);
        }
    }
}