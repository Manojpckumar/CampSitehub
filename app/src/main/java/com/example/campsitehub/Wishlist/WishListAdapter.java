package com.example.campsitehub.Wishlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.campsitehub.Homepage.HomePageAdapter;
import com.example.campsitehub.R;
import org.jetbrains.annotations.NotNull;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewholder> {

    Context context;


    public WishListAdapter(MyWishlistActivity myWishlistActivity) {
        this.context = myWishlistActivity;
    }

    @NonNull
    @NotNull
    @Override
    public WishListAdapter.MyViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.wishlist_card,parent,false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WishListAdapter.MyViewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        public MyViewholder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
