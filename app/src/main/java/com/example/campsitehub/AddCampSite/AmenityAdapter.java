package com.example.campsitehub.AddCampSite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.campsitehub.Amenities.AllAmenity;
import com.example.campsitehub.Bookings.MyBookingAdapter;
import com.example.campsitehub.CustomViews.CustomMainHeading;
import com.example.campsitehub.R;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Utils.RecyclerViewClickInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AmenityAdapter extends RecyclerView.Adapter<AmenityAdapter.MyViewHolder> {
    Context context;
    List<AllAmenity> allAmenityList;
    RecyclerViewClickInterface recyclerViewClickInterface;

    public AmenityAdapter(Context context, List<AllAmenity> allAmenityList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.allAmenityList = allAmenityList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @NotNull
    @Override


    public AmenityAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.all_amenityadapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AmenityAdapter.MyViewHolder holder, int position) {

        AllAmenity amenity=allAmenityList.get(position);
        holder.tv_am_price.setText("$ "+amenity.getAtPrice());
        holder.tv_amen_name.setText(amenity.getAtName());
        holder.tv_description.setText(amenity.getAtDescription());
        Glide.with(context).load(APIClient.baseUrl+"phase1/"+amenity.getAtBanner()).into(holder.image_Id);

    }

    @Override
    public int getItemCount() {
        return allAmenityList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

      CustomMainHeading tv_amen_name,tv_description,tv_am_price,Date_Id;
      ImageView image_Id,bK_delete,bK_edit;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_am_price=itemView.findViewById(R.id.tv_am_price);
            tv_description=itemView.findViewById(R.id.tv_description);
            Date_Id=itemView.findViewById(R.id.Date_Id);
            tv_amen_name=itemView.findViewById(R.id.tv_amen_name);
            bK_delete=itemView.findViewById(R.id.bK_delete);
            bK_edit=itemView.findViewById(R.id.bk_edit);
            image_Id=itemView.findViewById(R.id.image_Id);
            bK_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),"DELETE");
                }
            });

            bK_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),"EDIT");

                }
            });
        }


    }
}
