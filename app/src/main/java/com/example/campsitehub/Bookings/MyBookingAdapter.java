package com.example.campsitehub.Bookings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.campsitehub.R;
import com.example.campsitehub.Wishlist.WishListAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyViewHolder> {

    Context context;
    List<AllBooking> allBookings;

    public MyBookingAdapter(Context context, List<AllBooking> allBookings) {
        this.context = context;
        this.allBookings = allBookings;
    }

    public MyBookingAdapter(MyBookings myBookings) {
        this.context = myBookings;
    }

    @NonNull
    @NotNull
    @Override
    public MyBookingAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.wishlist_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyBookingAdapter.MyViewHolder holder, int position) {

        AllBooking allBooking=allBookings.get(position);

        holder.tv_campname.setText(allBooking.getTransactionId());

    }

    @Override
    public int getItemCount() {
        return allBookings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_campname;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_campname=itemView.findViewById(R.id.tv_campname);
        }
    }
}
