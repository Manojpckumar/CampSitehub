package com.example.campsitehub.CampDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.campsitehub.CustomViews.CustomSubHeading;
import com.example.campsitehub.R;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Utils.RecyclerViewClickInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.MyViewHolder> {

    Context context;
    List<Amenity> list;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public AmenitiesAdapter(Context context, List<Amenity> list, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @NotNull
    @Override
    public AmenitiesAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.amenities_card, parent, false);
        return new AmenitiesAdapter.MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull AmenitiesAdapter.MyViewHolder holder, int position) {

        Amenity model = list.get(position);

        Glide.with(context).load(APIClient.baseUrl + "phase1/" + model.getAtBanner()).into(holder.iv_amenities);
        holder.tv_amenities.setText(model.getAtName());
        holder.tv_rate.setText("$ " + model.getAtPrice());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_amenities;
        CustomSubHeading tv_amenities, tv_rate;
        CheckBox choose;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            iv_amenities = itemView.findViewById(R.id.iv_amenities);
            tv_amenities = itemView.findViewById(R.id.tv_amenities);
            choose = itemView.findViewById(R.id.choose_amen);
            tv_rate = itemView.findViewById(R.id.tv_rate);
            choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if(choose.isChecked()){

                        recyclerViewClickInterface.onItemClick(getAdapterPosition(),"");
                    }
                    else{
                        recyclerViewClickInterface.onItemClick(getAdapterPosition(),"uncheck");


                    }
                }
            });


        }
    }
}
