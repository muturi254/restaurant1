package com.example.restaurants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.restaurants.constants.Constant;
import com.example.restaurants.ui.RestaurantActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.findRestaurantButton) Button mFindRestaurantButton;
    @BindView(R.id.locationEditText) EditText mLocationEditText;

    private DatabaseReference mSearchedLocationReference;
    private ValueEventListener mSearchedLocationEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find views
       ButterKnife.bind(this);
       mFindRestaurantButton.setOnClickListener(this);

       // set up firebase
        mSearchedLocationReference = FirebaseDatabase.getInstance().getReference()
                .child(Constant.FIREBASE_CHILD_SEARCHED_LOCATION);

        mSearchedLocationEventListener = mSearchedLocationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot locationSnapShot: snapshot.getChildren()) {
                    String location = locationSnapShot.getValue().toString();
                    Log.d("Location updated", "location: " + location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedLocationReference.removeEventListener(mSearchedLocationEventListener);
    }
}