package kkm.com.core.model.response.register;

import com.google.gson.annotations.SerializedName;

public class ResponsePincode {

    @SerializedName("response")
    private String response;

    @SerializedName("pinCode")
    private String pinCode;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return
                "ResponsePincode{" +
                        "response = '" + response + '\'' +
                        ",pinCode = '" + pinCode + '\'' +
                        "}";
    }
}