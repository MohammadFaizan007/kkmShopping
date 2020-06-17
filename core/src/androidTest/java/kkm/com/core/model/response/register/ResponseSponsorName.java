package kkm.com.core.model.response.register;

import com.google.gson.annotations.SerializedName;

public class ResponseSponsorName {

    @SerializedName("displayName")
    private String displayName;

    @SerializedName("response")
    private String response;

    @SerializedName("message")
    private String message;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

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
}