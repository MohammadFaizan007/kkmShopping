package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class PostOfficeItem {

    @SerializedName("Circle")
    private String circle;

    @SerializedName("Description")
    private String description;

    @SerializedName("BranchType")
    private String branchType;

    @SerializedName("State")
    private String state;

    @SerializedName("DeliveryStatus")
    private String deliveryStatus;

    @SerializedName("Taluk")
    private String taluk;

    @SerializedName("Region")
    private String region;

    @SerializedName("Country")
    private String country;

    @SerializedName("Division")
    private String division;

    @SerializedName("District")
    private String district;

    @SerializedName("Name")
    private String name;

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getTaluk() {
        return taluk;
    }

    public void setTaluk(String taluk) {
        this.taluk = taluk;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}