 package com.example.campsitehub.Homepage;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.bumptech.glide.Glide;
import com.example.campsitehub.Authentication.LoginActivity;
import com.example.campsitehub.CampDetail.CampDetailActivity;
import com.example.campsitehub.CustomViews.CustomButton;
import com.example.campsitehub.CustomViews.CustomMainHeading;
import com.example.campsitehub.CustomViews.CustomSubHeading;
import com.example.campsitehub.CustomViews.CustomTextView;
import com.example.campsitehub.Parcelable.DataParcer;
import com.example.campsitehub.R;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.SplashScreen.SplashActivity;
import com.example.campsitehub.Utils.RecyclerViewClickInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

 public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.MyViewHolder> {

    List<HomeViewModel> list ;
    Context context;
    RecyclerViewClickInterface recyclerViewClickInterface;
     final Calendar myCalendar = Calendar.getInstance();
     public HomePageAdapter(List<HomeViewModel> list, Context context) {
         this.list = list;
         this.context = context;

     }


     @NonNull
    @Override
    public HomePageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.home_main_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePageAdapter.MyViewHolder holder, int position) {

        HomeViewModel model = list.get(position);

        holder.tv_campname.setText(model.getCampsiteName());
        holder.tv_amount.setText("$ "+model.getOfferPrice());
        holder.tv_strikeoff.setText("$ "+model.getOldPrice());
        holder.tv_strikeoff.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_likes.setText(model.getRatingNumber()+" peoples like this");
        Glide.with(context).load(APIClient.baseUrl+"phase1/" +model.getCampsiteBanner()).into(holder.iv_banner);
        Log.d("imgurl2200",APIClient.baseUrl+"phase1/"+model.getCampsiteBanner());

        holder.iv_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog myDialog = new Dialog(context);
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


                        new DatePickerDialog(context, date, myCalendar
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


                        new DatePickerDialog(context, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(tv_start.getText().toString().equals("")){
                            Toast.makeText(context, "Start date can't be empty", Toast.LENGTH_SHORT).show();

                        }
                        else if(tv_end.getText().toString().equals("")){
                            Toast.makeText(context, "End date can't be empty", Toast.LENGTH_SHORT).show();


                        }
                        else {
                            myDialog.dismiss();
                            Bundle args;
                            Fragment fragment;
                            args = new Bundle();
                            args.putString("edt_from", tv_start.getText().toString());
                            args.putString("edt_to", tv_end.getText().toString());
                            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
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




        });

//        holder.iv_banner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (model.getStatus()==1) {
//
//                    Intent intent = new Intent(context, CampDetailActivity.class);
//
//                    intent.putExtra("Book", String.valueOf(model.getId()));
//                    context.startActivity(intent);
//                }
//
//            }
//        });







    }

     @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomMainHeading tv_strikeoff,tv_amount,tv_campname;
        CustomSubHeading tv_status;
        CustomTextView tv_likes;
        LinearLayout ll_fab;
        ImageView iv_banner;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_strikeoff = itemView.findViewById(R.id.tv_strikeoff);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_campname = itemView.findViewById(R.id.tv_campname);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_likes = itemView.findViewById(R.id.tv_likes);
            ll_fab = itemView.findViewById(R.id.ll_fab);
            iv_banner = itemView.findViewById(R.id.iv_banner);
           iv_banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });



        }
    }
}
