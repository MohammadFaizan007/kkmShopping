package kkm.com.core.model.response.bill;

import com.google.gson.annotations.SerializedName;

public class BillPaymentProviderListItem {

    @SerializedName("providerName")
    private String providerName;

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}