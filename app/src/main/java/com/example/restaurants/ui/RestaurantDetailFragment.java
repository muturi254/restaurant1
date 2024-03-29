package com.example.restaurants.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurants.R;
import com.example.restaurants.constants.Constant;
import com.example.restaurants.models.Business;
import com.example.restaurants.models.Category;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantDetailFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.restaurantImageView)  ImageView mImageLabel;
    @BindView(R.id.restaurantNameTextView) TextView mNameLabel;
    @BindView(R.id.cuisineTextView) TextView mCategoriesLabel;
    @BindView(R.id.ratingTextView)  TextView mRatingLabel;
    @BindView(R.id.webTextView) TextView mWebsiteLabel;
    @BindView(R.id.phoneTextView) TextView mPhoneLabel;
    @BindView(R.id.addressTextView) TextView mAddressLabel;
    @BindView(R.id.saveRestaurantButton) Button mSaveRestaurantButton;

    private Business mRestaurant;

    public RestaurantDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RestaurantDetailFragment newInstance(Business restaurant) {
        RestaurantDetailFragment fragment = new RestaurantDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("restaurant", Parcels.wrap(restaurant));
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mRestaurant = Parcels.unwrap(getArguments().getParcelable("restaurant"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        ButterKnife.bind(this, view);
        Picasso.get().load(mRestaurant.getImageUrl()).into(mImageLabel);

        List<String> categories = new ArrayList<>();
        for (Category category: mRestaurant.getCategories()) {
            categories.add(category.getTitle());
        }

        mNameLabel.setText(mRestaurant.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", categories));
        mRatingLabel.setText(Double.toString(mRestaurant.getRating()) + "/5");
        mPhoneLabel.setText(mRestaurant.getPhone());
        mAddressLabel.setText(mRestaurant.getLocation().toString());

        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);
        mSaveRestaurantButton.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View view) {
        if(view == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mRestaurant.getUrl()));
            startActivity(webIntent);
        }
        if(view == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " +
                    mRestaurant.getPhone()));
            startActivity(phoneIntent);
        }
        if(view == mAddressLabel) {
            Intent locationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: " +
                    mRestaurant.getCoordinates().getLatitude()
            + mRestaurant.getCoordinates().getLongitude() + "?q=(" + mRestaurant.getName() + ")"));
            startActivity(locationIntent);
        }
        if(view == mSaveRestaurantButton) {
            DatabaseReference restaurantDatabaseReference = FirebaseDatabase.getInstance()
                    .getReference(Constant.FIREBASE_CHILD_RESTAURANT);
            restaurantDatabaseReference.push().setValue(mRestaurant);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
        }
    }
}