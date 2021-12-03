package com.example.campsitehub.PanoramicView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import co.gofynd.gravityview.GravityView;
import com.example.campsitehub.R;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Retrofit.GetResult;
import com.example.campsitehub.databinding.ActivityMyBookingsBinding;
import com.example.campsitehub.databinding.ActivityPanoramicViewBinding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import org.json.JSONException;
import org.json.JSONObject;

import okio.Options;
import retrofit2.Call;

public class PanoramicView extends AppCompatActivity implements GetResult.MyListener {

    VrPanoramaView vrPanoramaView;
    ActivityPanoramicViewBinding binding;
    private GravityView gravityView;
    private boolean supportvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_panoramic_view);

        binding = ActivityPanoramicViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getCampbyid(getIntent().getStringExtra("Camp_id"));
        init();
        if(supportvalue){
            this.gravityView.setImage(binding.ivImage,R.drawable.panoview).center();
        }
        else
        {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.panoview);
            binding.ivImage.setImageBitmap(bitmap);
        }



//        VrPanoramaView.Options options = new VrPanoramaView.Options();
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.panoview);
//        binding.vrPanoramaView.loadImageFromBitmap(bitmap, options);



    }
    private void getCampbyid(String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getDetails((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "campviewbyid");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        gravityView.registerListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gravityView.unRegisterListener();
    }

    public void init()
    {
    this.gravityView = GravityView.getInstance(getBaseContext());
    this.supportvalue = gravityView.deviceSupported();
    }

    @Override
    public void callback(JsonObject result, String callNo) {

    }
}