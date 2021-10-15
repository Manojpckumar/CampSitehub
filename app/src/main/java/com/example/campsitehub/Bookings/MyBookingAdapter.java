package com.example.campsitehub.Bookings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.campsitehub.R;
import com.example.campsitehub.Wishlist.WishListAdapter;
import org.jetbrains.annotations.NotNull;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyViewHolder> {

    Context context;

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

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
