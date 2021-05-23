package com.example.restaurants.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

public class MyRestaurantAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mRestaurants;
    private String[] mCuisines;

    public MyRestaurantAdapter(Context mContext, int resource, String[] restaurants, String[] cuisines) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mRestaurants = restaurants;
        this.mCuisines = cuisines;
    }

    @Override
    public int getCount() {
        return this.mRestaurants.length;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        String restaurant = this.mRestaurants[position];
        String cuisine = this.mCuisines[position];
        return String.format("%s serves great: %s", restaurant, cuisine);
    }
}
