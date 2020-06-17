package kkm.com.core.model.request.themeParkRequests;

import com.google.gson.annotations.SerializedName;

public class RequestThemeParkBook {

    @SerializedName("NUMBER")
    private String number;

    @SerializedName("Email")
    private String email;

    @SerializedName("pricing_name")
    private String pricingName;

    @SerializedName("ProductId")
    private String productId;

    @SerializedName("benMobile")
    private String benMobile;

    @SerializedName("Type")
    private String type;

    @SerializedName("tour_options_title")
    private String tourOptionsTitle;

    @SerializedName("AMOUNT")
    private String amount;

    @SerializedName("AMOUNT_ALL")
    private String amountall;

    @SerializedName("State")
    private String state;

    @SerializedName("benPostcode")
    private String benPostcode;

    @SerializedName("benlName")
    private String benlName;

    @SerializedName("benfName")
    private String benfName;

    @SerializedName("pricing_qty")
    private String pricingQty;

    @SerializedName("benCountryid")
    private String benCountryid;

    @SerializedName("time")
    private String time;

    @SerializedName("DateOfTour")
    private String dateOfTour;

    @SerializedName("benCity")
    private String benCity;

    @SerializedName("tour_options_Id")
    private String tourOptionsId;

    @SerializedName("benAddressline1")
    private String benAddressline1;

    @SerializedName("pricing_id")
    private String pricingId;

    @SerializedName("benAddressline2")
    private String benAddressline2;

    @SerializedName("FK_MemID")
    private String FK_MemID;

    public String getFK_MemID() {
        return FK_MemID;
    }

    public void setFK_MemID(String FK_MemID) {
        this.FK_MemID = FK_MemID;
    }

    public void setNUMBER(String nUMBER) {
        this.number = nUMBER;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPricingName() {
        return pricingName;
    }

    public void setPricingName(String pricingName) {
        this.pricingName = pricingName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBenMobile() {
        return benMobile;
    }

    public void setBenMobile(String benMobile) {
        this.benMobile = benMobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTourOptionsTitle() {
        return tourOptionsTitle;
    }

    public void setTourOptionsTitle(String tourOptionsTitle) {
        this.tourOptionsTitle = tourOptionsTitle;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmountall() {
        return amountall;
    }

    public void setAmountall(String amountall) {
        this.amountall = amountall;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBenPostcode() {
        return benPostcode;
    }

    public void setBenPostcode(String benPostcode) {
        this.benPostcode = benPostcode;
    }

    public String getBenlName() {
        return benlName;
    }

    public void setBenlName(String benlName) {
        this.benlName = benlName;
    }

    public String getBenfName() {
        return benfName;
    }

    public void setBenfName(String benfName) {
        this.benfName = benfName;
    }

    public String getPricingQty() {
        return pricingQty;
    }

    public void setPricingQty(String pricingQty) {
        this.pricingQty = pricingQty;
    }

    public String getBenCountryid() {
        return benCountryid;
    }

    public void setBenCountryid(String benCountryid) {
        this.benCountryid = benCountryid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDateOfTour() {
        return dateOfTour;
    }

    public void setDateOfTour(String dateOfTour) {
        this.dateOfTour = dateOfTour;
    }

    public String getBenCity() {
        return benCity;
    }

    public void setBenCity(String benCity) {
        this.benCity = benCity;
    }

    public String getTourOptionsId() {
        return tourOptionsId;
    }

    public void setTourOptionsId(String tourOptionsId) {
        this.tourOptionsId = tourOptionsId;
    }

    public String getBenAddressline1() {
        return benAddressline1;
    }

    public void setBenAddressline1(String benAddressline1) {
        this.benAddressline1 = benAddressline1;
    }

    public String getPricingId() {
        return pricingId;
    }

    public void setPricingId(String pricingId) {
        this.pricingId = pricingId;
    }

    public String getBenAddressline2() {
        return benAddressline2;
    }

    public void setBenAddressline2(String benAddressline2) {
        this.benAddressline2 = benAddressline2;
    }
}