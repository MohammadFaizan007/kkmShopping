package kkm.com.core.model.response.cart;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseShippingCharge {

    @SerializedName("response")
    private String response;

    @SerializedName("shippingCharges")
    private List<ShippingChargesItem> shippingCharges;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<ShippingChargesItem> getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(List<ShippingChargesItem> shippingCharges) {
        this.shippingCharges = shippingCharges;
    }
}