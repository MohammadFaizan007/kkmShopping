package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseWalletLedger {

    @SerializedName("eWalletLedgerlist")
    private List<EWalletLedgerlistItem> eWalletLedgerlist;

    @SerializedName("response")
    private String response;

    public List<EWalletLedgerlistItem> getEWalletLedgerlist() {
        return eWalletLedgerlist;
    }

    public void setEWalletLedgerlist(List<EWalletLedgerlistItem> eWalletLedgerlist) {
        this.eWalletLedgerlist = eWalletLedgerlist;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}