package kkm.com.core.model.response.giftCardResponse;

import com.google.gson.annotations.SerializedName;

public class ResponseCouponsDetails {

    @SerializedName("short_description")
    private String shortDescription;

    @SerializedName("images")
    private String images;

    @SerializedName("max_custom_price")
    private String maxCustomPrice;

    @SerializedName("tnc_mobile")
    private String tncMobile;

    @SerializedName("description")
    private String description;

    @SerializedName("min_custom_price")
    private String minCustomPrice;

    @SerializedName("paywithamazon_disable")
    private boolean paywithamazonDisable;

    @SerializedName("product_type")
    private String productType;

    @SerializedName("custom_denominations")
    private String customDenominations;

    @SerializedName("success")
    private boolean success;

    @SerializedName("name")
    private String name;

    @SerializedName("price_type")
    private String priceType;

    @SerializedName("tnc_web")
    private String tncWeb;

    @SerializedName("order_handling_charge")
    private int orderHandlingCharge;

    @SerializedName("id")
    private String id;

    @SerializedName("sku")
    private String sku;

    @SerializedName("tnc_mail")
    private String tncMail;

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getMaxCustomPrice() {
        return maxCustomPrice;
    }

    public void setMaxCustomPrice(String maxCustomPrice) {
        this.maxCustomPrice = maxCustomPrice;
    }

    public String getTncMobile() {
        return tncMobile;
    }

    public void setTncMobile(String tncMobile) {
        this.tncMobile = tncMobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinCustomPrice() {
        return minCustomPrice;
    }

    public void setMinCustomPrice(String minCustomPrice) {
        this.minCustomPrice = minCustomPrice;
    }

    public boolean isPaywithamazonDisable() {
        return paywithamazonDisable;
    }

    public void setPaywithamazonDisable(boolean paywithamazonDisable) {
        this.paywithamazonDisable = paywithamazonDisable;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getCustomDenominations() {
        return customDenominations;
    }

    public void setCustomDenominations(String customDenominations) {
        this.customDenominations = customDenominations;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public String getTncWeb() {
        return tncWeb;
    }

    public void setTncWeb(String tncWeb) {
        this.tncWeb = tncWeb;
    }

    public int getOrderHandlingCharge() {
        return orderHandlingCharge;
    }

    public void setOrderHandlingCharge(int orderHandlingCharge) {
        this.orderHandlingCharge = orderHandlingCharge;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTncMail() {
        return tncMail;
    }

    public void setTncMail(String tncMail) {
        this.tncMail = tncMail;
    }
}