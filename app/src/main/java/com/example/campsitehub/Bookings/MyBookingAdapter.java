package com.example.campsitehub.Bookings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyViewHolder> implements GetResult.MyListener {

    Context context;
    List<AllBooking> allBookings;
    String img_url="";


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
        holder.tv_transaction.setText(allBooking.getTransactionId());
        holder.tv_full_amnt.setText(allBooking.getTotal());
        getCampDetailsbyid(allBooking.getCampId(),holder);



    }
    private void getCampDetailsbyid(String campId, MyViewHolder holder) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", campId);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getDetails((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "campdetailsbyid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return allBookings.size();
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        if(callNo.equalsIgnoreCase("campdetailsbyid"))
        {
            Gson gson=new Gson();
            Example example=gson.fromJson(result.toString(),Example.class);
             img_url=APIClient.baseUrl+"phase1/" +example.getResultData().getCampDetails().getCampsiteBanner();
            Toast.makeText(context, img_url, Toast.LENGTH_SHORT).show();

        }



    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomMainHeading tv_campname,tv_transaction,tv_status,tv_full_amnt,bk_cancel,bk_next;
        ImageView camp_img;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_campname=itemView.findViewById(R.id.tv_campname);
            tv_transaction=itemView.findViewById(R.id.tv_txnid);
            tv_status=itemView.findViewById(R.id.tv_status);
            tv_full_amnt=itemView.findViewById(R.id.tv_full_amnt);
            bk_cancel=itemView.findViewById(R.id.bK_cancel);
            bk_next=itemView.findViewById(R.id.bk_info);
            camp_img=itemView.findViewById(R.id.camp_img);
        }
    }
}
