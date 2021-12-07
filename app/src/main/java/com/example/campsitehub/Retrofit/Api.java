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


    @POST(APIClient.APPEND_URL + "addAmenity")
    Call<JsonObject> addAmenity(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getAllAmenities")
    Call<JsonObject> getAllAmenities(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "del_amenity")
    Call<JsonObject> del_amenity(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "updateBookstatus")
    Call<JsonObject> UpdateBookstatus(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "updateAmenities")
    Call<JsonObject> updateAmenities(@Body JsonObject object);


    @POST(APIClient.APPEND_URL + "getAllCamps")
    Call<JsonObject> getAllCamps(@Body JsonObject object);


    @POST(APIClient.APPEND_URL + "updateCampstatus")
    Call<JsonObject> updateCampstatus(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "checkbookingexist")
    Call<JsonObject> checkbookingexist(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getAllSettings")
    Call<JsonObject> getAllSettings(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "updateSettings")
    Call<JsonObject> updateSettings(@Body JsonObject object);


}
