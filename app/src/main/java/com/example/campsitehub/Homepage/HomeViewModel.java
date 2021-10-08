package com.example.campsitehub.Homepage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeViewModel {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("campsite_name")
    @Expose
    private String campsiteName;
    @SerializedName("old_price")
    @Expose
    private String oldPrice;
    @SerializedName("offer_price")
    @Expose
    private String offerPrice;
    @SerializedName("rating_number")
    @Expose
    private String ratingNumber;
    @SerializedName("camp_type")
    @Expose
    private String campType;
    @SerializedName("status")
    @Expose
    private Long status;
    @SerializedName("campsite_banner")
    @Expose
    private String campsiteBanner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampsiteName() {
        return campsiteName;
    }

    public void setCampsiteName(String campsiteName) {
        this.campsiteName = campsiteName;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getRatingNumber() {
        return ratingNumber;
    }

    public void setRatingNumber(String ratingNumber) {
        this.ratingNumber = ratingNumber;
    }

    public String getCampType() {
        return campType;
    }

    public void setCampType(String campType) {
        this.campType = campType;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getCampsiteBanner() {
        return campsiteBanner;
    }

    public void setCampsiteBanner(String campsiteBanner) {
        this.campsiteBanner = campsiteBanner;
    }


}
