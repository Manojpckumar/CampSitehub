package com.example.campsitehub.Homepage;

import android.content.Context;
import android.content.Intent;
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
import com.example.campsitehub.CampDetail.CampDetailActivity;
import com.example.campsitehub.CustomViews.CustomMainHeading;
import com.example.campsitehub.CustomViews.CustomSubHeading;
import com.example.campsitehub.CustomViews.CustomTextView;
import com.example.campsitehub.R;
import com.example.campsitehub.Retrofit.APIClient;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    Context context;

    List<CampsDate> campsDates;


    public SearchAdapter(Context context, List<CampsDate> campsDates) {
        this.context = context;
        this.campsDates = campsDates;
    }

    @NonNull
    @NotNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.home_main_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchAdapter.MyViewHolder holder, int position) {

        CampsDate cd=campsDates.get(position);

        holder.tv_campname.setText(cd.getCampsiteName());
        holder.tv_amount.setText("$ "+cd.getOfferPrice());
        holder.tv_strikeoff.setText("$ "+cd.getOldPrice());
        holder.tv_strikeoff.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_likes.setText(cd.getRatingNumber()+" peoples like this");
        Glide.with(context).load(APIClient.baseUrl+"phase1/" +cd.getCampsiteBanner()).into(holder.iv_banner);
        Log.d("imgurl2200",APIClient.baseUrl+"phase1"+cd.getCampsiteBanner());
        if (cd.getStatus().equals("1"))
        {
            holder.tv_status.setText("AVAILABLE");
        }
        else
        {
            holder.tv_campname.setText("NOT AVAILABLE");
            holder.tv_campname.setBackgroundColor(ContextCompat.getColor(context,R.color.red));

        }

        holder.iv_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.getStatus().equals("1")) {

                    Intent intent = new Intent(context, CampDetailActivity.class);

                    intent.putExtra("Book", String.valueOf(cd.getId()));
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return campsDates.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomMainHeading tv_strikeoff,tv_amount,tv_campname;
        CustomSubHeading tv_status;
        CustomTextView tv_likes;
        LinearLayout ll_fab;
        ImageView iv_banner;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);


            tv_strikeoff = itemView.findViewById(R.id.tv_strikeoff);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_campname = itemView.findViewById(R.id.tv_campname);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_likes = itemView.findViewById(R.id.tv_likes);
            ll_fab = itemView.findViewById(R.id.ll_fab);
            iv_banner = itemView.findViewById(R.id.iv_banner);
        }
    }
}
