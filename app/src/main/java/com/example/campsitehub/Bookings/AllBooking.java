package com.example.campsitehub.Bookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllBooking {

    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("camp_id")
    @Expose
    private String campId;
    @SerializedName("amenity_id")
    @Expose
    private String amenityId;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("date_obooking")
    @Expose
    private String dateObooking;
    @SerializedName("time-obooking")
    @Expose
    private String timeObooking;
    @SerializedName("camp_name")
    @Expose
    private String campName;
    @SerializedName("camp_banner")
    @Expose
    private String campBanner;
    @SerializedName("amenity_name")
    @Expose
    private String amenityName;
    @SerializedName("status")
    @Expose
    private String status;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCampId() {
        return campId;
    }

    public void setCampId(String campId) {
        this.campId = campId;
    }

    public String getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(String amenityId) {
        this.amenityId = amenityId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDateObooking() {
        return dateObooking;
    }

    public void setDateObooking(String dateObooking) {
        this.dateObooking = dateObooking;
    }

    public String getTimeObooking() {
        return timeObooking;
    }

    public void setTimeObooking(String timeObooking) {
        this.timeObooking = timeObooking;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public String getCampBanner() {
        return campBanner;
    }

    public void setCampBanner(String campBanner) {
        this.campBanner = campBanner;
    }

    public String getAmenityName() {
        return amenityName;
    }

    public void setAmenityName(String amenityName) {
        this.amenityName = amenityName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
