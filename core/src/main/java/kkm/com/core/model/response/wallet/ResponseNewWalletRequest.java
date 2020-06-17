package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

public class ResponseNewWalletRequest {

    @SerializedName("response")
    private String response;

    @SerializedName("pK_RequestId")
    private String pK_RequestId;

    @SerializedName("message")
    private String message;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getpK_RequestId() {
        return pK_RequestId;
    }
}