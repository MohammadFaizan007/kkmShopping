package kkm.com.core.model.request.utility.response;

import com.google.gson.annotations.SerializedName;

public class ResponseWalletLedgerUtility {

    @SerializedName("narration")
    private String narration;

    @SerializedName("transDate")
    private String transDate;

    @SerializedName("debitAmount")
    private String debitAmount;

    @SerializedName("creditAmount")
    private String creditAmount;

    @SerializedName("balanceAmount")
    private String balanceAmount;

    @SerializedName("memberId")
    private String memberId;

    @SerializedName("status")
    private String status;

    public String getNarration() {
        return narration;
    }

    public String getTransDate() {
        return transDate;
    }

    public String getDebitAmount() {
        return debitAmount;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getStatus() {
        return status;
    }
}