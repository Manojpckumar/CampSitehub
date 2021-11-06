package com.example.campsitehub.CampDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AmenityDetails {

    @SerializedName("at_id")
    @Expose
    private String atId;
    @SerializedName("at_name")
    @Expose
    private String atName;
    @SerializedName("at_banner")
    @Expose
    private String atBanner;
    @SerializedName("at_description")
    @Expose
    private String atDescription;
    @SerializedName("at_price")
    @Expose
    private String atPrice;
    @SerializedName("camp_id")
    @Expose
    private String campId;

    public String getAtId() {
        return atId;
    }

    public void setAtId(String atId) {
        this.atId = atId;
    }

    public String getAtName() {
        return atName;
    }

    public void setAtName(String atName) {
        this.atName = atName;
    }

    public String getAtBanner() {
        return atBanner;
    }

    public void setAtBanner(String atBanner) {
        this.atBanner = atBanner;
    }

    public String getAtDescription() {
        return atDescription;
    }

    public void setAtDescription(String atDescription) {
        this.atDescription = atDescription;
    }

    public String getAtPrice() {
        return atPrice;
    }

    public void setAtPrice(String atPrice) {
        this.atPrice = atPrice;
    }

    public String getCampId() {
        return campId;
    }

    public void setCampId(String campId) {
        this.campId = campId;
    }

}