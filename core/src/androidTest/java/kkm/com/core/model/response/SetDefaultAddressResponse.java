package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class SetDefaultAddressResponse {

    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return
                "SetDefaultAddressResponse{" +
                        "response = '" + response + '\'' +
                        "}";
    }
}