package com.example.campsitehub.Bookings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campsitehub.CampDetail.Example;
import com.example.campsitehub.CustomViews.CustomMainHeading;
import com.example.campsitehub.R;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Retrofit.GetResult;
import com.example.campsitehub.Utils.RecyclerViewClickInterface;
import com.example.campsitehub.Wishlist.WishListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyViewHolder> {

    Context context;
    List<AllBooking> allBookings;
    FirebaseUser user;
    RecyclerViewClickInterface recyclerViewClickInterface;


    public MyBookingAdapter(Context context, List<AllBooking> allBookings, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.allBookings = allBookings;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    public MyBookingAdapter(MyBookings myBookings) {
        this.context = myBookings;
    }

    @NonNull
    @NotNull
    @Override
    public MyBookingAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.wishlist_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyBookingAdapter.MyViewHolder holder, int position) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        AllBooking allBooking = allBookings.get(position);
        holder.tv_transaction.setText(allBooking.getTransactionId());
        holder.tv_full_amnt.setText(allBooking.getTotal());
        holder.tv_campname.setText(allBooking.getCampName());

        if (user.getEmail().equals("admin@gmail.com")) {

            holder.bk_cancel.setVisibility(View.GONE);
        }

        if (allBooking.getStatus().equals("0")) {
            holder.tv_status.setText("Pending");

        } else if (allBooking.getStatus().equals("1")) {

            holder.tv_status.setText("Confirmed");
            holder.edit_layout.setVisibility(View.GONE);


        } else if (allBooking.getStatus().equals("2")) {

            holder.tv_status.setText("Cancelled By admin");
            holder.edit_layout.setVisibility(View.GONE);

        } else {
            holder.tv_status.setText("Cancelled");
            holder.edit_layout.setVisibility(View.GONE);


        }


    }


    @Override
    public int getItemCount() {
        return allBookings.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomMainHeading tv_campname, tv_transaction, tv_status, tv_full_amnt, bk_cancel, bk_next;
        LinearLayout admin_confirmation, edit_layout;
        RadioGroup radioGroup;
        Button proceed;
        RadioButton radioButton;
        String val = "";

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_campname = itemView.findViewById(R.id.tv_campname);
            tv_transaction = itemView.findViewById(R.id.tv_txnid);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_full_amnt = itemView.findViewById(R.id.tv_full_amnt);
            bk_cancel = itemView.findViewById(R.id.bK_cancel);
            bk_next = itemView.findViewById(R.id.bk_info);
            admin_confirmation = itemView.findViewById(R.id.admin_confirmation);
            edit_layout = itemView.findViewById(R.id.edit_layout);
            radioGroup = itemView.findViewById(R.id.rd_grp_booking);
            proceed = itemView.findViewById(R.id.proceed);


            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                      @Override
                                                      public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                          radioButton = itemView.findViewById(checkedId);

                                                      }
                                                  }
            );

            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edit_layout.setVisibility(View.VISIBLE);
                    admin_confirmation.setVisibility(View.GONE);
                    if (radioButton.getText().toString().equals("Confirm")) {
                        val = "1";
                        recyclerViewClickInterface.onItemClick(getAdapterPosition(), val);
                    }
                else if (radioButton.getText().toString().equals("Initiate Refund")) {

                        val = "2";
                        recyclerViewClickInterface.onItemClick(getAdapterPosition(), val);
                    }
                     else {
                        val = "3";
                        recyclerViewClickInterface.onItemClick(getAdapterPosition(), val);

                    }


                }
            });
            bk_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (user.getEmail().equals("admin@gmail.com")) {

                        admin_confirmation.setVisibility(View.VISIBLE);
                        edit_layout.setVisibility(View.GONE);

                    }
                }
            });

        }
    }
}
