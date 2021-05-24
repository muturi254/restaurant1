package com.example.restaurants.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurants.R;
import com.example.restaurants.models.Business;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {
    private List<Business> mRestaurant;
    private Context mContext;

    public RestaurantListAdapter(Context context, List<Business> restaurant) {
        mContext = context;
        mRestaurant = restaurant;
    }

    @Override
    public int getItemCount() {
        return mRestaurant.size();
    }

    @Override
    public void onBindViewHolder(RestaurantListAdapter.RestaurantViewHolder holder, int position) {
        holder.bindRestaurant(mRestaurant.get(position));
    }

    @Override
    public RestaurantListAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_item, parent, false);
        RestaurantViewHolder viewHolder = new RestaurantViewHolder(view);
        return viewHolder;
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurantImageView) ImageView mRestaurantImageView;
        @BindView(R.id.restaurantNameTextView) TextView mNameTextView;
        @BindView(R.id.categoryTextView) TextView mCategoryTextView;
        @BindView(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindRestaurant(Business restaurant) {
            Picasso.get().load(restaurant.getImageUrl()).into(mRestaurantImageView);
            mNameTextView.setText(restaurant.getName());
            mCategoryTextView.setText(restaurant.getCategories().get(0).getTitle());
            mRatingTextView.setText("Rating: " + restaurant.getRating() + "/5");
        }

    }
}
