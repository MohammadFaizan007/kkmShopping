package kkm.com.core.model.request.utility;

import com.google.gson.annotations.SerializedName;

public class RequestWalletLedgerDetails {

    @SerializedName("MemberId")
    private String memberId;

    @SerializedName("Type")
    private String type;

    @SerializedName("FromDate")
    private String fromDate;

    @SerializedName("ToDate")
    private String toDate;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return
                "RequestWalletLedgerDetails{" +
                        "memberId = '" + memberId + '\'' +
                        ",type = '" + type + '\'' +
                        ",fromDate = '" + fromDate + '\'' +
                        ",toDate = '" + toDate + '\'' +
                        "}";
    }
}