package com.example.restaurants.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurants.adapters.RestaurantListAdapter;
import com.example.restaurants.models.Business;
import com.example.restaurants.models.Category;
import com.example.restaurants.adapters.MyRestaurantAdapter;
import com.example.restaurants.R;
import com.example.restaurants.models.YelpBusinessesSearchResponse;
import com.example.restaurants.network.YelpApi;
import com.example.restaurants.network.YelpClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends AppCompatActivity {
    // @BindView(R.id.listView) ListView mListView;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.locationTextView) TextView mLocationTextView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;


    private RestaurantListAdapter restaurantListAdapter;
//    private String[] restaurants = new String[] {"Mi Mero Mole", "Mother's Bistro",
//            "Life of Pie", "Screen Door", "Luc Lac", "Sweet Basil",
//            "Slappy Cakes", "Equinox", "Miss Delta's", "Andina",
//            "Lardo", "Portland City Grill", "Fat Head's Brewery",
//            "Chipotle", "Subway"};
//    private String[] cuisines = new String[] {"Vegan Food", "Breakfast", "Fishs Dishs",
//            "Scandinavian", "Coffee", "English Food", "Burgers", "Fast Food", "Noodle Soups",
//            "Mexican", "BBQ", "Cuban", "Bar Food", "Sports Bar", "Breakfast", "Mexican" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        // find view
        ButterKnife.bind(this);

//        MyRestaurantAdapter adapter = new MyRestaurantAdapter(this, android.R.layout.simple_list_item_1, restaurants, cuisines);
//        mListView.setAdapter(adapter);

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String restaurant = ((TextView) view).getText().toString();
//                Toast.makeText(RestaurantActivity.this, restaurant, Toast.LENGTH_LONG).show();
//            }
//        });

        // get intent content
        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        // mLocationTextView.setText(location);

        YelpApi client = YelpClient.getClient();
        Call<YelpBusinessesSearchResponse> call = client.getRestaurants(location, "restaurant");
        call.enqueue(new Callback<YelpBusinessesSearchResponse>() {
            @Override
            public void onResponse(Call<YelpBusinessesSearchResponse> call, Response<YelpBusinessesSearchResponse> response) {
                if (response.isSuccessful()) {

                    hideProgressBar();

                    assert response.body() != null;
                    List<Business> restaurantsList = response.body().getBusinesses();
                    String[] restaurants = new String[restaurantsList.size()];
                    String[] categories = new String[restaurantsList.size()];

                    for(int i=0; i < restaurants.length; i++) {
                        restaurants[i] = restaurantsList.get(i).getName();
                    }
                    for(int i=0; i < categories.length; i++) {
                        Category category = restaurantsList.get(i).getCategories().get(0);
                        categories[i] = category.getTitle();
                    }

                    restaurantListAdapter = new RestaurantListAdapter(RestaurantActivity.this, restaurants);

                    showRestaurants();
                } else {
                    showUnsuccesfulMessage();
                }
            }

            @Override
            public void onFailure(Call<YelpBusinessesSearchResponse> call, Throwable t) {
                hideProgressBar();
                showFailureMessage();
            }
        });
    }

    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccesfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showRestaurants() {
        mListView.setVisibility(View.VISIBLE);
        mLocationTextView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}