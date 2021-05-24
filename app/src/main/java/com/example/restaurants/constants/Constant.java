package com.example.restaurants.constants;

import com.example.restaurants.BuildConfig;

public class Constant {
    public static final String YELP_BASE_URL = "https://api.yelp.com/v3/";
    public static final String YELP_API_KEY = BuildConfig.YELP_API_KEY;
    public static final String PREFERENCES_LOCATION_KEY = "location";
    public static final String FIREBASE_CHILD_SEARCHED_LOCATION = "searchedTerm";
    public static final String FIREBASE_CHILD_RESTAURANT = "restaurants";
}
