package kkm.com.core.model.response.themeParkResponse.themeParkDetails;

import com.google.gson.annotations.SerializedName;

public class AddinfoItem {

    @SerializedName("country")
    private String country;

    @SerializedName("productId")
    private String productId;

    @SerializedName("city")
    private String city;

    @SerializedName("price")
    private String price;

    @SerializedName("name")
    private String name;

    public String getCountry() {
        return country;
    }

    public String getProductId() {
        return productId;
    }

    public String getCity() {
        return city;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}