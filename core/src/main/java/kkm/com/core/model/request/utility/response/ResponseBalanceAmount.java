package kkm.com.core.model.request.utility.response;

import com.google.gson.annotations.SerializedName;

public class ResponseBalanceAmount {

//    {
//        "memberId": 23772,
//            "balanceAmount": 0,
//            "status": "Success"
//    }

    @SerializedName("BalanceAmount")
    private float balanceAmount;

    @SerializedName("MemberId")
    private int memberId;

    @SerializedName("Status")
    private String status;

    public float getBalanceAmount() {
        return balanceAmount;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getStatus() {
        return status;
    }
}