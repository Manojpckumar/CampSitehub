package com.example.campsitehub.Homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campsitehub.Authentication.LoginActivity;
import com.example.campsitehub.Interface.OnFragmentInteractionListener;
import com.example.campsitehub.R;
import com.example.campsitehub.databinding.ActivityMain2Binding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    DrawerLayout drawerLayout;
    ActivityMain2Binding binding;
    ///ContentMainBinding contentMainBinding;
    TextView txt_user_123;
    NavigationView navigationViewz;

    FrameLayout frameLayout;
    FirebaseAuth auth;
    TextView tv_userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

       // hideItem();

        String utype = getIntent().getStringExtra("types");
        auth = FirebaseAuth.getInstance();
        frameLayout = (FrameLayout) findViewById(R.id.FrameLayout);

        navigationViewz = (NavigationView) findViewById(R.id.nav_view);
        tv_userEmail = (TextView) findViewById(R.id.tv_userEmail);
        View headerView = navigationViewz.inflateHeaderView(R.layout.nav_drawer_header);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawerLayout = binding.drawerLayout;

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        // NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

//        load frontend
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getEmail();

//        tv_userEmail.setText(userid);

        if(userid.equals("admin@gmail.com"))
        {
            LoadFragment(new AdminHomeFragment());
            hideItem();
            binding.includercv.inctoolbar.etSearch.setVisibility(View.GONE);
            binding.includercv.inctoolbar.ivSearch.setVisibility(View.GONE);
        }
        else
        {
            LoadFragment(new HomeFragment());
            hideItem();
        }

        hideItem();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int menuid = menuItem.getItemId();
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (menuid) {

            case R.id.nav_home:
                LoadFragment(new HomeFragment());
                break;

            case R.id.nav_adminLogout:
                auth.signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                break;

            case R.id.nav_userLogout:
                auth.signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                break;
        }
        return false;
    }

    private void LoadFragment(Fragment homeFragment) {

        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.FrameLayout, homeFragment);
        ft.commit();
        Log.d("test2255","1111"+getIntent().getStringExtra("email"));

    }

    public void hideItem()
    {
        Menu nav_Menu = binding.navView.getMenu();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getEmail();

        if(userid.equals("admin@gmail.com"))
        {
            nav_Menu.findItem(R.id.nav_userhome).setVisible(false);
            nav_Menu.findItem(R.id.nav_userBookings).setVisible(false);
            nav_Menu.findItem(R.id.nav_userWishList).setVisible(false);
            nav_Menu.findItem(R.id.nav_userProfile).setVisible(false);
            nav_Menu.findItem(R.id.nav_userLogout).setVisible(false);
        }
        else
        {
            nav_Menu.findItem(R.id.nav_adminHome).setVisible(false);
            nav_Menu.findItem(R.id.nav_adminManageBooking).setVisible(false);
            nav_Menu.findItem(R.id.nav_adminManageUsers).setVisible(false);
            nav_Menu.findItem(R.id.nav_adminManageReview).setVisible(false);
            nav_Menu.findItem(R.id.nav_adminProfile).setVisible(false);
            nav_Menu.findItem(R.id.nav_adminLogout).setVisible(false);
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}