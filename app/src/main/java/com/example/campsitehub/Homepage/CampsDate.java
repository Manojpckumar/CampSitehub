package com.example.campsitehub.Homepage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CampsDate {

    @SerializedName("id")
    @Expose
    private String id;
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
    private String status;
    @SerializedName("campsite_banner")
    @Expose
    private String campsiteBanner;
    @SerializedName("realted_images")
    @Expose
    private String realtedImages;
    @SerializedName("available_dates")
    @Expose
    private String availableDates;
    @SerializedName("available_dates_to")
    @Expose
    private String availableDatesTo;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("panoramic")
    @Expose
    private String panoramic;
    @SerializedName("location")
    @Expose
    private String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCampsiteBanner() {
        return campsiteBanner;
    }

    public void setCampsiteBanner(String campsiteBanner) {
        this.campsiteBanner = campsiteBanner;
    }

    public String getRealtedImages() {
        return realtedImages;
    }

    public void setRealtedImages(String realtedImages) {
        this.realtedImages = realtedImages;
    }

    public String getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(String availableDates) {
        this.availableDates = availableDates;
    }

    public String getAvailableDatesTo() {
        return availableDatesTo;
    }

    public void setAvailableDatesTo(String availableDatesTo) {
        this.availableDatesTo = availableDatesTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPanoramic() {
        return panoramic;
    }

    public void setPanoramic(String panoramic) {
        this.panoramic = panoramic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
