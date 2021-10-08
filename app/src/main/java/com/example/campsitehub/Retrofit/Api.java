package com.example.campsitehub.Retrofit;

import com.example.campsitehub.Homepage.ExampleModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("phase1/v1/Api.php?apicall=fetchHomeView")
    Call<ExampleModel> GetHomeData();

}
