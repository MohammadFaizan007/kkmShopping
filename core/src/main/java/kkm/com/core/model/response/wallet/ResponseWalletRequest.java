package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseWalletRequest {

    @SerializedName("response")
    private String response;

    @SerializedName("eWalletRequestlist")
    private List<EWalletRequestlistItem> eWalletRequestlist;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<EWalletRequestlistItem> getEWalletRequestlist() {
        return eWalletRequestlist;
    }

    public void setEWalletRequestlist(List<EWalletRequestlistItem> eWalletRequestlist) {
        this.eWalletRequestlist = eWalletRequestlist;
    }
}