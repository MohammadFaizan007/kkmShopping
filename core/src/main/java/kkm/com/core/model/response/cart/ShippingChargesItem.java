package kkm.com.core.model.response.cart;

import com.google.gson.annotations.SerializedName;

public class ShippingChargesItem {

    @SerializedName("thresholdAmt")
    private String thresholdAmt;

    @SerializedName("shippingAmt")
    private String shippingAmt;

    public String getThresholdAmt() {
        return thresholdAmt;
    }

    public void setThresholdAmt(String thresholdAmt) {
        this.thresholdAmt = thresholdAmt;
    }

    public String getShippingAmt() {
        return shippingAmt;
    }

    public void setShippingAmt(String shippingAmt) {
        this.shippingAmt = shippingAmt;
    }
}