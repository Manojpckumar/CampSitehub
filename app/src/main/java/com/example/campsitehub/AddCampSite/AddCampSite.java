package com.example.campsitehub.AddCampSite;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.campsitehub.R;
import com.example.campsitehub.ResponseCommon;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Retrofit.Api;
import com.example.campsitehub.Retrofit.GetResult;
import com.example.campsitehub.Utils.CustPrograssbar;
import com.example.campsitehub.databinding.ActivityAddCampSiteBinding;
import com.example.campsitehub.databinding.ActivityMyBookingsBinding;
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

public class AddCampSite extends AppCompatActivity implements View.OnClickListener, GetResult.MyListener {

    ActivityAddCampSiteBinding binding;
    private final int CGALLERY = 1,CPANO=2,CRELAT=3;
    String campencodedImage="",imgname="",panoencodedImage="",panoimgname="",relatedencodedImage="",relaimgname="";
    CustPrograssbar custPrograssbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddCampSiteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);




        custPrograssbar = new CustPrograssbar();
        binding.btnCreatecampsite.setOnClickListener(this);
        binding.btnBanner.setOnClickListener(this);
        binding.btnRelatedimg.setOnClickListener(this);
        binding.btnPanoimg.setOnClickListener(this);
        requestPermissions();

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
                     imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == CRELAT) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    binding.imgRelated.setVisibility(View.VISIBLE);
                    binding.imgRelated.setImageBitmap(bitmap);
                    relatedencodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    relaimgname = String.valueOf(Calendar.getInstance().getTimeInMillis());


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == CPANO) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    binding.imgPano.setVisibility(View.VISIBLE);
                    binding.imgPano.setImageBitmap(bitmap);
                    panoencodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    panoimgname = String.valueOf(Calendar.getInstance().getTimeInMillis());


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_createcampsite:

                uploadDatatoServer();
                break;

            case R.id.btn_banner:

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(galleryIntent, CGALLERY);


                break;

            case R.id.btn_panoimg:
                Intent galleryIntent1 = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(galleryIntent1, CPANO);


                break;

            case R.id.btn_relatedimg:
                Intent galleryIntent2 = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(galleryIntent2, CRELAT);

                break;

        }




    }

    private void uploadDatatoServer() {

        custPrograssbar.progressCreate(this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("campsite_name", binding.cedCampname.getText().toString());
            jsonObject.put("old_price", binding.cedOldprice.getText().toString());
            jsonObject.put("offer_price", binding.cedOfferprice.getText().toString());
            jsonObject.put("rating_number", binding.rbRating.getRating());
            jsonObject.put("camp_type", binding.spnCamptype.getSelectedItem().toString());
            if(binding.tbStatus.isChecked()){
                jsonObject.put("status", 1);
            }
            else{
                jsonObject.put("status", 0);
            }

            jsonObject.put("campsite_banner", campencodedImage);
            jsonObject.put("available_dates", binding.cedAvailabledates.getText().toString());
            jsonObject.put("description", binding.cedDescription.getText().toString());
            jsonObject.put("panoramic", panoencodedImage);
            jsonObject.put("realted_image", relatedencodedImage);
            jsonObject.put("location", binding.cedLocation.getText().toString());
            jsonObject.put("image_name", imgname);
            jsonObject.put("related_img", relaimgname);
            jsonObject.put("panoramic_img", panoimgname);



            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().addCamps((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "addcamp");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if(callNo.equalsIgnoreCase("addcamp")){
            Gson gson = new Gson();
            ResponseCommon response = gson.fromJson(result.toString(), ResponseCommon.class);
            custPrograssbar.close();
            Toast.makeText(this, response.getResponseMsg(), Toast.LENGTH_SHORT).show();


        }

    }
}