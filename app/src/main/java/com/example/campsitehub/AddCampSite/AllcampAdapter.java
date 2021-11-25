package com.example.campsitehub.AddCampSite;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.campsitehub.CampDetail.AdminCamp;
import com.example.campsitehub.CustomViews.CustomMainHeading;
import com.example.campsitehub.CustomViews.CustomSubHeading;
import com.example.campsitehub.CustomViews.CustomTextView;
import com.example.campsitehub.R;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Utils.RecyclerViewClickInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AllcampAdapter extends RecyclerView.Adapter<AllcampAdapter.MyViewHolder>  {

    Context context;
    List<AdminCamp> campList;
    RecyclerViewClickInterface recyclerViewClickInterface;

    public AllcampAdapter(Context context, List<AdminCamp> campList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.campList = campList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @NotNull
    @Override
    public AllcampAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.home_main_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllcampAdapter.MyViewHolder holder, int position) {

        AdminCamp model=campList.get(position);

        holder.tv_campname.setText(model.getCampsiteName());
        holder.tv_amount.setText("$ "+model.getOfferPrice());
        holder.tv_strikeoff.setText("$ "+model.getOldPrice());
        holder.tv_strikeoff.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_likes.setText(model.getRatingNumber()+" peoples like this");
        Glide.with(context).load(APIClient.baseUrl+"phase1/" +model.getCampsiteBanner()).into(holder.iv_banner);
        Log.d("imgurl2200",APIClient.baseUrl+"phase1"+model.getCampsiteBanner());
        if (model.getStatus() .equals("1"))
        {
            holder.tv_status.setText("AVAILABLE");
        }
        else
        {
            holder.tv_status.setText("NOT AVAILABLE");
            holder.tv_status.setBackgroundColor(ContextCompat.getColor(context,R.color.red));

        }

    }

    @Override
    public int getItemCount() {
        return campList.size();
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
            tv_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tv_status.getText().toString().equals("AVAILABLE")) {
                        recyclerViewClickInterface.onItemClick(getAdapterPosition(), "0");
                    }
                    else{

                        recyclerViewClickInterface.onItemClick(getAdapterPosition(), "1");

                    }
                }
            });



        }
    }
}
