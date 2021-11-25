package com.example.campsitehub.Amenities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.campsitehub.AddCampSite.Allamenities;
import com.example.campsitehub.CampDetail.Example;
import com.example.campsitehub.R;
import com.example.campsitehub.ResponseCommon;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Retrofit.GetResult;
import com.example.campsitehub.Utils.CustPrograssbar;
import com.example.campsitehub.databinding.ActivityAddAmenitiesBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;

public class AddAmenities extends AppCompatActivity implements GetResult.MyListener,View.OnClickListener {

    ActivityAddAmenitiesBinding binding;
    CustPrograssbar custPrograssbar;
    private final int CGALLERY = 1;
    String image_name="",campencodedImage="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddAmenitiesBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        requestPermissions();
        custPrograssbar=new CustPrograssbar();
        binding.btnCreateamenity.setOnClickListener(this);
        binding.btnAmenitybanner.setOnClickListener(this);
        String action_value=getIntent().getStringExtra("key");
        if(action_value.equals("0")){


        }else{
            custPrograssbar.progressCreate(this);
            custPrograssbar.setCancel(false);
            getamenbyid(action_value);
            binding.btnCreateamenity.setText("UPDATE");
            binding.btnAmenitybanner.setEnabled(false
            );
        }

    }

    private void getamenbyid(String action) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("am_id", action);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAmenitybyid((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "amenitiesbyid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void  requestPermissions(){
        Dexter.withActivity(this)
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == CGALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    binding.imgCamp.setVisibility(View.VISIBLE);
                    binding.imgCamp.setImageBitmap(bitmap);
                    campencodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    image_name = String.valueOf(Calendar.getInstance().getTimeInMillis());


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }




    }


    @Override
    public void callback(JsonObject result, String callNo) {

        if(callNo.equalsIgnoreCase("addAmenity")){

            Gson gson = new Gson();
            ResponseCommon response = gson.fromJson(result.toString(), ResponseCommon.class);
            custPrograssbar.close();
            Toast.makeText(this, response.getResponseMsg(), Toast.LENGTH_SHORT).show();

        }
        else if(callNo.equalsIgnoreCase("amenitiesbyid")){
            custPrograssbar.close();
            Gson gson = new Gson();
            Example am = gson.fromJson(result.toString(), Example.class);
            binding.amenityName.setText(am.getResultData().getAmenityDetails().getAtName());
            binding.amenityOfferprice.setText(am.getResultData().getAmenityDetails().getAtPrice());
            binding.amenDescription.setText(am.getResultData().getAmenityDetails().getAtDescription());
            Glide.with(this).load(APIClient.baseUrl+"phase1/"+am.getResultData().getAmenityDetails().getAtBanner()).into(binding.imgCamp);


        }

        else if(callNo.equalsIgnoreCase("updateAmenity")){

            Gson gson=new Gson();
            ResponseCommon responseCommon=gson.fromJson(result.toString(),ResponseCommon.class);
            if(responseCommon.getResult().equals("true")){

                startActivity(new Intent(this, Allamenities.class));
            }
        }
        else{
            Toast.makeText(this, "Inavlid calls", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){


            case R.id.btn_amenitybanner:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(galleryIntent, CGALLERY);


                break;


            case R.id.btn_createamenity:
                if(binding.btnCreateamenity.getText().toString().equals("UPDATE"))
                {
                    if (binding.amenityName.getText().toString().isEmpty()) {

                        binding.amenityName.setError("Please enter the Camp Name to proceed");


                    } else if (binding.amenityOfferprice.getText().toString().isEmpty()) {

                        binding.amenityOfferprice.setError("Please enter the Price to proceed");

                    } else if (binding.amenDescription.getText().toString().isEmpty()) {

                        binding.amenDescription.setError("Please enter a short description to proceed");

                    } else{

                        custPrograssbar.progressCreate(this);
                        custPrograssbar.setCancel(false);
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("amenity_name", binding.amenityName.getText().toString());
                            jsonObject.put("amenity_price", binding.amenityOfferprice.getText().toString());
                            jsonObject.put("amenity_desc", binding.amenDescription.getText().toString());
                            jsonObject.put("at_id",getIntent().getStringExtra("key") );
                            JsonParser jsonParser = new JsonParser();
                            Call<JsonObject> call = APIClient.getInterface().updateAmenities((JsonObject) jsonParser.parse(jsonObject.toString()));
                            GetResult getResult = new GetResult();
                            getResult.setMyListener(this);
                            getResult.onNCHandle(call, "updateAmenity");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }



                }
                else {
                    if (binding.amenityName.getText().toString().isEmpty()) {

                        binding.amenityName.setError("Please enter the Camp Name to proceed");


                    } else if (binding.amenityOfferprice.getText().toString().isEmpty()) {

                        binding.amenityOfferprice.setError("Please enter the Price to proceed");

                    } else if (binding.amenDescription.getText().toString().isEmpty()) {

                        binding.amenDescription.setError("Please enter a short description to proceed");

                    } else if (campencodedImage.isEmpty()) {

                        Toast.makeText(this, "Please Attach an image to proceed", Toast.LENGTH_SHORT).show();

                    } else {

                        custPrograssbar.progressCreate(this);
                        custPrograssbar.setCancel(false);
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("amenity_name", binding.amenityName.getText().toString());
                            jsonObject.put("amenity_price", binding.amenityOfferprice.getText().toString());
                            jsonObject.put("amenity_desc", binding.amenDescription.getText().toString());
                            jsonObject.put("image", campencodedImage);
                            jsonObject.put("image_name", image_name);
                            JsonParser jsonParser = new JsonParser();
                            Call<JsonObject> call = APIClient.getInterface().addAmenity((JsonObject) jsonParser.parse(jsonObject.toString()));
                            GetResult getResult = new GetResult();
                            getResult.setMyListener(this);
                            getResult.onNCHandle(call, "addAmenity");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }


                break;
        }

    }
}