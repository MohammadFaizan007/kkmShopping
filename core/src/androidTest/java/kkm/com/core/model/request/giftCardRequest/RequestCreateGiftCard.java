package kkm.com.core.model.request.giftCardRequest;

import com.google.gson.annotations.SerializedName;

public class RequestCreateGiftCard {

    @SerializedName("lName")
    private String lName;

    @SerializedName("NUMBER")
    private String nUMBER;

    @SerializedName("Email")
    private String email;

    @SerializedName("Product_id")
    private String productId;

    @SerializedName("Producttype")
    private String producttype;

    @SerializedName("Amount")
    private String amount;

    @SerializedName("benMobile")
    private String benMobile;

    @SerializedName("giftmessage")
    private String giftmessage;

    @SerializedName("Type")
    private String type;

    @SerializedName("Amount_All")
    private String amountAll;

    @SerializedName("fName")
    private String fName;

    @SerializedName("price")
    private String price;

    @SerializedName("billing_email")
    private String billingEmail;

    @SerializedName("qty")
    private String qty;

    @SerializedName("benlName")
    private String benlName;

    @SerializedName("benfName")
    private String benfName;

    @SerializedName("theme")
    private String theme;

    @SerializedName("FK_MemID")
    private String FK_MemID;

    public String getFK_MemID() {
        return FK_MemID;
    }

    public void setFK_MemID(String FK_MemID) {
        this.FK_MemID = FK_MemID;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public void setNUMBER(String nUMBER) {
        this.nUMBER = nUMBER;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setBenMobile(String benMobile) {
        this.benMobile = benMobile;
    }

    public void setGiftmessage(String giftmessage) {
        this.giftmessage = giftmessage;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmountAll(String amountAll) {
        this.amountAll = amountAll;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setBenlName(String benlName) {
        this.benlName = benlName;
    }

    public void setBenfName(String benfName) {
        this.benfName = benfName;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}