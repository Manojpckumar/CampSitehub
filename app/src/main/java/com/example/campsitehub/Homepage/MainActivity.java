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

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campsitehub.Authentication.LoginActivity;
import com.example.campsitehub.Bookings.MyBookings;
import com.example.campsitehub.CustomViews.CustomEditText;
import com.example.campsitehub.Interface.OnFragmentInteractionListener;
import com.example.campsitehub.R;
import com.example.campsitehub.Wishlist.MyWishlistActivity;
import com.example.campsitehub.databinding.ActivityMain2Binding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener, View.OnClickListener {

    DrawerLayout drawerLayout;
    ActivityMain2Binding binding;
    NavigationView navigationViewz;
    public static MainActivity homeActivity = null;
    FrameLayout frameLayout;
    FirebaseAuth auth;
    Fragment fragment;
    Bundle args;
    TextView tv_userEmail;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.includercv.inctoolbar.ettoSearch.setCursorVisible(false);
        binding.includercv.inctoolbar.etfromSearch.setCursorVisible(false);
        // hideItem();
        homeActivity = this;
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

        initViews(binding);

        if (userid.equals("admin@gmail.com")) {
            LoadFragment(new AdminHomeFragment());
            hideItem();
            binding.includercv.inctoolbar.etfromSearch.setVisibility(View.GONE);
            binding.includercv.inctoolbar.ettoSearch.setVisibility(View.GONE);
            binding.includercv.inctoolbar.ivSearch.setVisibility(View.GONE);
        } else {
            LoadFragment(new HomeFragment());
            hideItem();
        }

        hideItem();

    }

    private void initViews(ActivityMain2Binding binding) {
        binding.includercv.inctoolbar.ettoSearch.setOnClickListener(this);
        binding.includercv.inctoolbar.etfromSearch.setOnClickListener(this);
        binding.includercv.inctoolbar.ivSearch.setOnClickListener(this);
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
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;

            case R.id.nav_userLogout:
                auth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;

            case R.id.nav_userWishList:
                startActivity(new Intent(MainActivity.this, MyWishlistActivity.class));
                finish();
                break;

            case R.id.nav_userBookings:
                startActivity(new Intent(MainActivity.this, MyBookings.class));
                finish();
                break;


        }
        return false;
    }

    private void showDpdialog() {


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };


        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (binding.includercv.inctoolbar.etfromSearch.getText().toString().isEmpty()) {
            binding.includercv.inctoolbar.etfromSearch.setText(sdf.format(myCalendar.getTime()));
        } else {
            binding.includercv.inctoolbar.ettoSearch.setText(sdf.format(myCalendar.getTime()));


        }


    }

    private void LoadFragment(Fragment homeFragment) {

        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.FrameLayout, homeFragment);
        ft.commit();
        Log.d("test2255", "1111" + getIntent().getStringExtra("email"));

    }

    public void callFragment(Fragment fragmentClass) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.FrameLayout, fragmentClass).addToBackStack("adds").commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public static MainActivity getInstance() {
        return homeActivity;
    }

    public void hideItem() {
        Menu nav_Menu = binding.navView.getMenu();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getEmail();

        if (userid.equals("admin@gmail.com")) {
            nav_Menu.findItem(R.id.nav_userhome).setVisible(false);
            nav_Menu.findItem(R.id.nav_userBookings).setVisible(false);
            nav_Menu.findItem(R.id.nav_userWishList).setVisible(false);
            nav_Menu.findItem(R.id.nav_userProfile).setVisible(false);
            nav_Menu.findItem(R.id.nav_userLogout).setVisible(false);
        } else {
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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.etfrom_search:

                showDpdialog();
                break;


            case R.id.etto_search:
                showDpdialog();

                break;


            case R.id.iv_search:

                if (binding.includercv.inctoolbar.etfromSearch.getText().toString().isEmpty() && binding.includercv.inctoolbar.etfromSearch.getText().toString().isEmpty()) {

                    Toast.makeText(homeActivity, "Search cant be empty", Toast.LENGTH_SHORT).show();

                }
                else if( binding.includercv.inctoolbar.ivSearch.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.quantum_ic_close_white_24).getConstantState()){

                    binding.includercv.inctoolbar.etfromSearch.setText("");
                    binding.includercv.inctoolbar.ettoSearch.setText("");
                    getSupportFragmentManager().popBackStackImmediate();

                }

                else {
                    binding.includercv.inctoolbar.ivSearch.setImageDrawable(getDrawable(R.drawable.quantum_ic_close_white_24));

                    args = new Bundle();
                    args.putString("edt_from", binding.includercv.inctoolbar.etfromSearch.getText().toString());
                    args.putString("edt_to", binding.includercv.inctoolbar.ettoSearch.getText().toString());
                    fragment = new CampSearchFragment();
                    fragment.setArguments(args);
                    callFragment(fragment);

                }

                break;
        }

    }
}