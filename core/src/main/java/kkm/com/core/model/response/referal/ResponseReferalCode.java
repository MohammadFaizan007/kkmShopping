package kkm.com.core.model.response.referal;

import com.google.gson.annotations.SerializedName;

public class ResponseReferalCode {

    @SerializedName("response")
    private String response;

    @SerializedName("referalName")
    private String referalName;

    public String getResponse() {
        return response;
    }

    public String getReferalName() {
        return referalName;
    }
}