package kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityArray;

import com.google.gson.annotations.SerializedName;

public class TourOptionsItem {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("pricing")
    private Pricing pricing;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }

    @Override
    public String toString() {
        return
                "TourOptionsItem{" +
                        "id = '" + id + '\'' +
                        ",title = '" + title + '\'' +
                        ",pricing = '" + pricing + '\'' +
                        "}";
    }
}