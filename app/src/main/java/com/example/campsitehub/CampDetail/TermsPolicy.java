package com.example.campsitehub.CampDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TermsPolicy {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("terms")
    @Expose
    private String terms;
    @SerializedName("privacy")
    @Expose
    private String privacy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

}