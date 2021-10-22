package com.example.campsitehub.CampDetail;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultData {

    @SerializedName("Camp_details")
    @Expose
    private CampDetails campDetails;
    @SerializedName("amenities")
    @Expose
    private List<Amenity> amenities = null;

    public CampDetails getCampDetails() {
        return campDetails;
    }

    public void setCampDetails(CampDetails campDetails) {
        this.campDetails = campDetails;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

}