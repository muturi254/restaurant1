package com.example.restaurants.adapters;

import android.support.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.restaurants.models.Business;
import com.example.restaurants.ui.RestaurantDetailFragment;

import java.util.List;

public class RestaurantPagerAdapter  extends FragmentPagerAdapter {

    private List<Business> mRestaurants;


    public RestaurantPagerAdapter(@NonNull FragmentManager fm, int behaviour, List<Business> restaurant) {
        super(fm, behaviour);
        mRestaurants = restaurant;
    }

    @Override
    public int getCount() {
        return mRestaurants.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mRestaurants.get(position).getName();
    }


    @Override
    public Fragment getItem(int position) {
        return RestaurantDetailFragment.newInstance(mRestaurants.get(position));
    }
}
