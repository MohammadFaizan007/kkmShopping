package kkm.com.core.model.response.themeParkResponse.themeParkDetails;

import com.google.gson.annotations.SerializedName;

public class AddinfoItem {

    @SerializedName("Country")
    private String country;

    @SerializedName("ProductId")
    private String productId;

    @SerializedName("City")
    private String city;

    @SerializedName("Price")
    private String price;

    @SerializedName("Name")
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