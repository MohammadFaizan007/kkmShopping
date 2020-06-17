package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class ProductListItem {

    @SerializedName("availability")
    private String availability;

    @SerializedName("bV")
    private String bV;

    @SerializedName("productName")
    private String productName;

    @SerializedName("productImage")
    private String productImage;

    @SerializedName("productOldPrice")
    private String productOldPrice;

    @SerializedName("productId")
    private String productId;

    @SerializedName("productPrice")
    private String productPrice;

    @SerializedName("productDescription")
    private String productDescription;

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getBV() {
        return bV;
    }

    public void setBV(String bV) {
        this.bV = bV;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductOldPrice() {
        return productOldPrice;
    }

    public void setProductOldPrice(String productOldPrice) {
        this.productOldPrice = productOldPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}