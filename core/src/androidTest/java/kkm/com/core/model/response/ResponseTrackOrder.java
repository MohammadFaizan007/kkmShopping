package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseTrackOrder {

    @SerializedName("response")
    private String response;

    @SerializedName("orderTracking")
    private OrderTracking orderTracking;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public OrderTracking getOrderTracking() {
        return orderTracking;
    }

    public void setOrderTracking(OrderTracking orderTracking) {
        this.orderTracking = orderTracking;
    }

    @Override
    public String toString() {
        return
                "ResponseTrackOrder{" +
                        "response = '" + response + '\'' +
                        ",orderTracking = '" + orderTracking + '\'' +
                        "}";
    }
}