package com.example.campsitehub.Wishlist;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.campsitehub.Homepage.HomePageAdapter;
import com.example.campsitehub.R;
import com.example.campsitehub.databinding.ActivityMain2Binding;
import com.example.campsitehub.databinding.ActivityMyWishlistBinding;

public class MyWishlistActivity extends AppCompatActivity {

    ActivityMyWishlistBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyWishlistBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        WishListAdapter adapter = new WishListAdapter(this);
        binding.rcvWishlist.setLayoutManager(new LinearLayoutManager(this));

        binding.rcvWishlist.setAdapter(adapter);


    }
}