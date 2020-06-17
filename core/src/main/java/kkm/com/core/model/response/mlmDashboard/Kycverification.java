package kkm.com.core.model.response.mlmDashboard;

import com.google.gson.annotations.SerializedName;

public class Kycverification {

    @SerializedName("bankStatus")
    private String bankStatus;

    @SerializedName("adharStatus")
    private String adharStatus;

    @SerializedName("panCardStatus")
    private String panCardStatus;

    public String getBankStatus() {
        return bankStatus;
    }

    public String getAdharStatus() {
        return adharStatus;
    }

    public String getPanCardStatus() {
        return panCardStatus;
    }
}