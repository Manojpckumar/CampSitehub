package com.example.campsitehub.CampDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.campsitehub.CustomViews.CustomSubHeading;
import com.example.campsitehub.Homepage.HomePageAdapter;
import com.example.campsitehub.R;
import com.example.campsitehub.Retrofit.APIClient;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.MyViewHolder> {

    Context context;
    List<Amenity> list ;

    public AmenitiesAdapter(Context context, List<Amenity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public AmenitiesAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.amenities_card,parent,false);
        return new AmenitiesAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AmenitiesAdapter.MyViewHolder holder, int position) {

        Amenity  model = list.get(position);

        Glide.with(context).load(APIClient.baseUrl +model.getAtBanner()).into(holder.iv_amenities);
        holder.tv_amenities.setText(model.getAtName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_amenities;
        CustomSubHeading tv_amenities;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            iv_amenities = itemView.findViewById(R.id.iv_amenities);
            tv_amenities = itemView.findViewById(R.id.tv_amenities);
        }
    }
}
