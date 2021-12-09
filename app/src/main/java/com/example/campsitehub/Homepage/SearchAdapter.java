package com.example.campsitehub.Homepage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.campsitehub.CampDetail.CampDetailActivity;
import com.example.campsitehub.CustomViews.CustomMainHeading;
import com.example.campsitehub.CustomViews.CustomSubHeading;
import com.example.campsitehub.CustomViews.CustomTextView;
import com.example.campsitehub.R;
import com.example.campsitehub.ResponseCommon;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Retrofit.GetResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    Context context;
    List<CampsDate> campsDates;
    String edt_from, edt_to;


    public SearchAdapter(Context context, List<CampsDate> campsDates, String edt_from, String edt_to) {
        this.context = context;
        this.campsDates = campsDates;
        this.edt_from = edt_from;
        this.edt_to = edt_to;
    }

    @NonNull
    @NotNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_main_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchAdapter.MyViewHolder holder, int position) {

        CampsDate cd = campsDates.get(position);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("from_date", edt_from);
            jsonObject.put("to_date", edt_to);
            jsonObject.put("camp_id", cd.getId());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAvailabilityStatus((JsonObject) jsonParser.parse(jsonObject.toString()));

            call.enqueue(new Callback<JsonObject>() {
                @Override

                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Gson gson = new Gson();
                    JsonObject res = response.body();
                    ResponseCommon responseCommon = gson.fromJson(res.toString(), ResponseCommon.class);
                    if (responseCommon.getResult().equals("true")) {
                        holder.tv_status.setText("NOT AVAILABLE WITHIN DATES");
                        holder.tv_status.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                        holder.frame_tot.setAlpha((float) 0.6);
                        holder.iv_banner.setEnabled(false);

                    } else {

                        holder.tv_status.setText("AVAILABLE");

                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    ;
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


        holder.tv_campname.setText(cd.getCampsiteName());
        holder.tv_amount.setText("$ " + cd.getOfferPrice());
        holder.tv_strikeoff.setText("$ " + cd.getOldPrice());
        holder.tv_strikeoff.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_likes.setText(cd.getRatingNumber() + " peoples like this");
        Glide.with(context).load(APIClient.baseUrl + "phase1/" + cd.getCampsiteBanner()).into(holder.iv_banner);
        holder.iv_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.getStatus().equals("1")) {

                    Intent intent = new Intent(context, CampDetailActivity.class);
                    intent.putExtra("Book", String.valueOf(cd.getId()));
                    intent.putExtra("from", edt_from);
                    intent.putExtra("to", edt_to);
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

        CustomMainHeading tv_strikeoff, tv_amount, tv_campname;
        CustomSubHeading tv_status;
        CustomTextView tv_likes;
        LinearLayout ll_fab;
        ImageView iv_banner;
        FrameLayout frame_tot;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);


            tv_strikeoff = itemView.findViewById(R.id.tv_strikeoff);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_campname = itemView.findViewById(R.id.tv_campname);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_likes = itemView.findViewById(R.id.tv_likes);
            ll_fab = itemView.findViewById(R.id.ll_fab);
            iv_banner = itemView.findViewById(R.id.iv_banner);
            frame_tot = itemView.findViewById(R.id.frame_tot);
        }
    }
}
