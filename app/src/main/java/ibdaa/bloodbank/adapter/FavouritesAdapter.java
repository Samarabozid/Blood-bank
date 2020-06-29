package ibdaa.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ibdaa.bloodbank.R;
import ibdaa.bloodbank.data.model.favorites.FavoritesData;
import ibdaa.bloodbank.view.activity.BaseActivity;

import static ibdaa.bloodbank.data.api.RetrofitClient.getClient;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouriteHolder>  {
    private static final String API_TOKEN = "Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27";
    private List<FavoritesData> favouritePostsList = new ArrayList<>();
    Context context;
    private BaseActivity activity;
    // private ArrayList<PostsData> postsListFull;

    public FavouritesAdapter(Activity activity, List<FavoritesData> favouritePostsList) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.favouritePostsList = favouritePostsList;
    }

    @NonNull
    @Override
    public FavouritesAdapter.FavouriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article,null);
        FavouriteHolder favouriteHolder = new FavouriteHolder(view);
        return favouriteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesAdapter.FavouriteHolder holder, final int position) {
        Glide.with(context).load(favouritePostsList.get(position).getThumbnailFullPath())
                .into(holder.postBackgroundImage);
        holder.tvTitleOfPost.setText(favouritePostsList.get(position).getTitle());
        if (favouritePostsList.get(position).getIsFavourite()) {
            holder.imBuIsFav.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            holder.imBuIsFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }

        holder.imBuIsFav.setOnClickListener(v -> {
            getClient().addAndRemoveFavourite(favouritePostsList.get(position).getId(),API_TOKEN);
        });
    }

    @Override
    public int getItemCount() {
        return favouritePostsList.size();
    }

    public void filterList(ArrayList<FavoritesData> filteredList){
        favouritePostsList = filteredList;
        notifyDataSetChanged();
    }


    public class FavouriteHolder extends RecyclerView.ViewHolder {
        ImageView postBackgroundImage;
        TextView tvTitleOfPost;
        ImageView imBuIsFav;
        public FavouriteHolder(@NonNull View itemView) {
            super(itemView);
            postBackgroundImage = (ImageView) itemView.findViewById(R.id.post_image_view);
            tvTitleOfPost = (TextView) itemView.findViewById(R.id.post_txt_view);
            imBuIsFav = (ImageView) itemView.findViewById(R.id.post_fav);

        }
    }

       /* @Override
    public Filter getFilter() {
        return postFilter;
    }

    private Filter postFilter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<PostsData> filtereredList =new ArrayList<>();

            if (constraint == null || constraint.length()==0){
                filtereredList.addAll(postsListFull);

            }

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };*/
}
