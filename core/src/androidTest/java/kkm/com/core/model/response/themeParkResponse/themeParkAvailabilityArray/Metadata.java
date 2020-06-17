package kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityArray;

import com.google.gson.annotations.SerializedName;

public class Metadata {

    @SerializedName("exchange")
    private Exchange exchange;

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public String toString() {
        return
                "Metadata{" +
                        "exchange = '" + exchange + '\'' +
                        "}";
    }
}