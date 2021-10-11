package com.example.campsitehub.CampDetail;

public class AmenitiesModel {

    String amenity;
    int image;

    public AmenitiesModel() {
    }

    public AmenitiesModel(String amenity, int image) {
        this.amenity = amenity;
        this.image = image;
    }

    public String getAmenity() {
        return amenity;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
