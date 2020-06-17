package kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityArray;

import com.google.gson.annotations.SerializedName;

public class Exchange {

    @SerializedName("currency")
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return
                "Exchange{" +
                        "currency = '" + currency + '\'' +
                        "}";
    }
}