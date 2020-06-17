package kkm.com.core.model.response.profile;

import com.google.gson.annotations.SerializedName;

public class ResponseProfileUpdate {

    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}