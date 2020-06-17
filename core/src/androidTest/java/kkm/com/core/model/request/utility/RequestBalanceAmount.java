package kkm.com.core.model.request.utility;

import com.google.gson.annotations.SerializedName;

public class RequestBalanceAmount {

    @SerializedName("MemberId")
    private String memberId;

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}