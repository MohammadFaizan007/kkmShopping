package kkm.com.core.model.request.utility.response;

import com.google.gson.annotations.SerializedName;

public class ResponseBalanceAmount {

    @SerializedName("balanceAmount")
    private int balanceAmount;

    @SerializedName("memberId")
    private int memberId;

    @SerializedName("status")
    private String status;

    public int getBalanceAmount() {
        return balanceAmount;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getStatus() {
        return status;
    }
}