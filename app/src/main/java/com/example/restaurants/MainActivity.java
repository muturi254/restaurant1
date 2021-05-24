package com.example.restaurants;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.restaurants.constants.Constant;
import com.example.restaurants.ui.RestaurantActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.findRestaurantButton) Button mFindRestaurantButton;
    @BindView(R.id.locationEditText) EditText mLocationEditText;

    private DatabaseReference mSearchedLocationReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find views
       ButterKnife.bind(this);
       mFindRestaurantButton.setOnClickListener(this);

       // set up firebase
        mSearchedLocationReference = FirebaseDatabase.getInstance().getReference().child(Constant.FIREBASE_CHILD_SEARCHED_LOCATION);

    }

    @Override
    public void onClick(View v) {
        if (v==mFindRestaurantButton) {
            String location = mLocationEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
            intent.putExtra("location", location);
            saveLocationToFireBase(location);
            startActivity(intent);
        }
    }

    public void saveLocationToFireBase(String location) {
        mSearchedLocationReference.push().setValue(location);
    }
}