package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAddressListResponse {

    @SerializedName("response")
    private String response;

    @SerializedName("getAddressList")
    private List<GetAddressListItem> getAddressList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<GetAddressListItem> getGetAddressList() {
        return getAddressList;
    }

    public void setGetAddressList(List<GetAddressListItem> getAddressList) {
        this.getAddressList = getAddressList;
    }

    @Override
    public String toString() {
        return
                "getAddressListResponse{" +
                        "response = '" + response + '\'' +
                        ",getAddressList = '" + getAddressList + '\'' +
                        "}";
    }
}