package com.example.campsitehub.Bookings;


import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.campsitehub.R;
import com.example.campsitehub.Wishlist.WishListAdapter;
import com.example.campsitehub.databinding.ActivityMyBookingsBinding;
import com.example.campsitehub.databinding.ActivityMyWishlistBinding;

public class MyBookings extends AppCompatActivity {

    ActivityMyBookingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_bookings);

        binding = ActivityMyBookingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        MyBookingAdapter adapter = new MyBookingAdapter(this);
        binding.rcvMybooking.setLayoutManager(new LinearLayoutManager(this));

        binding.rcvMybooking.setAdapter(adapter);
    }
}