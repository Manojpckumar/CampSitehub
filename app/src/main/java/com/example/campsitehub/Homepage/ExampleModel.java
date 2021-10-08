package com.example.campsitehub.Homepage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleModel {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("HomeView")
    @Expose
    private List<HomeViewModel> homeView = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HomeViewModel> getHomeView() {
        return homeView;
    }

    public void setHomeView(List<HomeViewModel> homeView) {
        this.homeView = homeView;
    }

}
