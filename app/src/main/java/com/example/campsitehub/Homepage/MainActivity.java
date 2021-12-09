package com.example.campsitehub.Homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campsitehub.Authentication.LoginActivity;
import com.example.campsitehub.Bookings.MyBookings;
import com.example.campsitehub.CustomViews.CustomEditText;
import com.example.campsitehub.CustomViews.CustomTextView;
import com.example.campsitehub.Interface.OnFragmentInteractionListener;
import com.example.campsitehub.R;
import com.example.campsitehub.Utils.CommonUtils;
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
        // hideItem()
        homeActivity = this;
        String utype = getIntent().getStringExtra("types");
        auth = FirebaseAuth.getInstance();
        frameLayout = (FrameLayout) findViewById(R.id.FrameLayout);

        navigationViewz = (NavigationView) findViewById(R.id.nav_view);
        tv_userEmail = (TextView) findViewById(R.id.tv_userEmail);


        View headerView = navigationViewz.inflateHeaderView(R.layout.nav_drawer_header);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawerLayout = binding.drawerLayout;

        //setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.hide();

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

        } else {
            LoadFragment(new HomeFragment());
            hideItem();
        }

        hideItem();

    }

    private void initViews(ActivityMain2Binding binding) {

        binding.includercv.inctoolbar.dateCh.setOnClickListener(this);


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
            nav_Menu.findItem(R.id.nav_userProfile).setVisible(false);
            nav_Menu.findItem(R.id.nav_userLogout).setVisible(false);
            nav_Menu.findItem(R.id.nav_adminManageReview).setVisible(false);
            nav_Menu.findItem(R.id.nav_adminManageUsers).setVisible(false);
            binding.includercv.inctoolbar.dateCh.setVisibility(View.GONE);
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
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.date_ch:

                showd();


                break;



        }

    }

    private void showd() {


                Dialog myDialog = new Dialog(this);
                myDialog.getWindow();
                myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                myDialog.setCancelable(true);
                myDialog.setContentView(R.layout.dialogbox);
                Button button = (Button) myDialog.findViewById(R.id.btn_check);
                CustomTextView tv_start = (CustomTextView) myDialog.findViewById(R.id.Edittext_start);
                CustomTextView tv_end = (CustomTextView) myDialog.findViewById(R.id.Edittext_return);
                LinearLayout linear1 = (LinearLayout) myDialog.findViewById(R.id.linear1);
                LinearLayout linear12 = (LinearLayout) myDialog.findViewById(R.id.linear2);
                linear1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                // TODO Auto-generated method stub
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                String myFormat = "yyyy-MM-dd";
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                                if (tv_start.getText().toString().isEmpty()) {
                                    tv_start.setText(sdf.format(myCalendar.getTime()));
                                } else {
                                    tv_end.setText(sdf.format(myCalendar.getTime()));


                                }

                            }

                        };


                        new DatePickerDialog(MainActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                    }
                });
                linear12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                // TODO Auto-generated method stub
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                String myFormat = "yyyy-MM-dd";
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                                if (tv_start.getText().toString().isEmpty()) {
                                    tv_start.setText(sdf.format(myCalendar.getTime()));
                                } else {
                                    tv_end.setText(sdf.format(myCalendar.getTime()));


                                }

                            }

                        };


                        new DatePickerDialog(MainActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(tv_start.getText().toString().equals("")){
                            Toast.makeText(MainActivity.this, "Start date can't be empty", Toast.LENGTH_SHORT).show();

                        }
                        else if(tv_end.getText().toString().equals("")){
                            Toast.makeText(MainActivity.this, "End date can't be empty", Toast.LENGTH_SHORT).show();


                        }
                        else {
                            myDialog.dismiss();
                            Bundle args;
                            Fragment fragment;
                            args = new Bundle();
                            args.putString("edt_from", tv_start.getText().toString());
                            args.putString("edt_to", tv_end.getText().toString());

                            FragmentManager fragmentManager = ((FragmentActivity) MainActivity.this).getSupportFragmentManager();
                            fragment = new CampSearchFragment();
                            fragment.setArguments(args);
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.FrameLayout, fragment);
                            fragmentTransaction.commit();
                        }

                    }
                });
                myDialog.show();
            }






}