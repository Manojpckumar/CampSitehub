package com.example.campsitehub.CampDetail;

import java.util.List;

import com.example.campsitehub.Amenities.AllAmenity;
import com.example.campsitehub.Bookings.AllBooking;
import com.example.campsitehub.Homepage.CampsDate;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultData {

    @SerializedName("Camp_details")
    @Expose
    private CampDetails campDetails;

    @SerializedName("Amenity_details")
    @Expose
    private AmenityDetails amenityDetails;

    @SerializedName("amenities")
    @Expose
    private List<Amenity> amenities = null;

    @SerializedName("CampsDate")
    @Expose
    private List<CampsDate> campsDate = null;

    @SerializedName("All Bookings")
    @Expose
    private List<AllBooking> allBookings = null;

    @SerializedName("All_Amenity")
    @Expose
    private List<AllAmenity> allAmenity = null;

    @SerializedName("AdminCamps")
    @Expose
    private List<AdminCamp> adminCamps = null;

    @SerializedName("TermsPolicy")
    @Expose
    private TermsPolicy termsPolicy;

    public TermsPolicy getTermsPolicy() {
        return termsPolicy;
    }

    public void setTermsPolicy(TermsPolicy termsPolicy) {
        this.termsPolicy = termsPolicy;
    }

    public List<AdminCamp> getAdminCamps() {
        return adminCamps;
    }

    public void setAdminCamps(List<AdminCamp> adminCamps) {
        this.adminCamps = adminCamps;
    }

    public CampDetails getCampDetails() {
        return campDetails;
    }

    public void setCampDetails(CampDetails campDetails) {
        this.campDetails = campDetails;
    }

    public AmenityDetails getAmenityDetails() {
        return amenityDetails;
    }

    public void setCampDetails(AmenityDetails amenityDetails) {
        this.amenityDetails = amenityDetails;
    }

    public List<CampsDate> getCampsDate() {
        return campsDate;
    }

    public void setCampsDate(List<CampsDate> campsDate) {
        this.campsDate = campsDate;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public List<AllBooking> getAllBookings() {
        return allBookings;
    }

    public void setAllBookings(List<AllBooking> allBookings) {
        this.allBookings = allBookings;
    }

    public List<AllAmenity> getAllAmenity() {
        return allAmenity;
    }

    public void setAllAmenity(List<AllAmenity> allAmenity) {
        this.allAmenity = allAmenity;
    }

}