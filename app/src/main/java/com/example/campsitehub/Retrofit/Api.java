package com.example.campsitehub.Retrofit;

import com.example.campsitehub.CampDetail.Example;
import com.example.campsitehub.Homepage.ExampleModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @GET("phase1/v1/Api.php?apicall=fetchHomeView")
    Call<ExampleModel> GetHomeData();


    @FormUrlEncoded
    @POST("phase1/Api.php?apicall=getCampDetailsbyid")
    Call<Example> GetCampDetailById(@Field("branch_code") int id);


    @POST(APIClient.APPEND_URL + "getCampDetailsbyid")
    Call<JsonObject> getDetails(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "addCampViews")
    Call<JsonObject> addCamps(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getAmenDetailsbyid")
    Call<JsonObject> getAmenitybyid(@Body JsonObject object);



    @POST(APIClient.APPEND_URL + "getCampsbyavailabledate")
    Call<JsonObject> getCampDatebyid(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "addBooking")
    Call<JsonObject> addBooking(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getAllBookings")
    Call<JsonObject> GetBookings(@Body JsonObject object);


}
