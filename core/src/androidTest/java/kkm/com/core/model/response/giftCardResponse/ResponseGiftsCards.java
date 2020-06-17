package kkm.com.core.model.response.giftCardResponse;

import com.google.gson.annotations.SerializedName;

public class ResponseGiftsCards {

    @SerializedName("images")
    private String image;

    @SerializedName("max_custom_price")
    private String maxCustomPrice;

    @SerializedName("custom_denominations")
    private String customDenominations;

    @SerializedName("name")
    private String name;

    @SerializedName("price_type")
    private String priceType;

    @SerializedName("id")
    private String id;

    @SerializedName("min_custom_price")
    private String minCustomPrice;

    public String getImages() {
        return image;
    }

    public void setImages(String image) {
        this.image = image;
    }

    public String getMaxCustomPrice() {
        return maxCustomPrice;
    }

    public void setMaxCustomPrice(String maxCustomPrice) {
        this.maxCustomPrice = maxCustomPrice;
    }

    public String getCustomDenominations() {
        return customDenominations;
    }

    public void setCustomDenominations(String customDenominations) {
        this.customDenominations = customDenominations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMinCustomPrice() {
        return minCustomPrice;
    }

    public void setMinCustomPrice(String minCustomPrice) {
        this.minCustomPrice = minCustomPrice;
    }
}