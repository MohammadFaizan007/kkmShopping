package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class HomePageItem {

    @SerializedName("distributionPrice")
    private String distributionPrice;

    @SerializedName("brandName")
    private String brandName;

    @SerializedName("offerName")
    private String offerName;

    @SerializedName("offerPrice")
    private String offerPrice;

    @SerializedName("productImage")
    private String productImage;

    @SerializedName("productId")
    private String productId;

    @SerializedName("brandId")
    private String brandId;

    @SerializedName("mrp")
    private String mrp;

    @SerializedName("categoryName")
    private String categoryName;

    @SerializedName("fk_CategoryId")
    private String fkCategoryId;

    public void setDistributionPrice(String distributionPrice) {
        this.distributionPrice = distributionPrice;
    }

    public String getDistributionPrice() {
        return distributionPrice;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getMrp() {
        return mrp;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setFkCategoryId(String fkCategoryId) {
        this.fkCategoryId = fkCategoryId;
    }

    public String getFkCategoryId() {
        return fkCategoryId;
    }
}