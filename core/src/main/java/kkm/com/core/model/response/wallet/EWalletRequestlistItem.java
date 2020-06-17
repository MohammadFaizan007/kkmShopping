package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

public class EWalletRequestlistItem {

    @SerializedName("approvalDate")
    private String approvalDate;

    @SerializedName("requestedAmount")
    private String requestedAmount;

    @SerializedName("requestedDate")
    private String requestedDate;

    @SerializedName("slipUpload")
    private String slipUpload;

    @SerializedName("requestNo")
    private String requestNo;

    @SerializedName("paymentStatus")
    private String paymentStatus;

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(String requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getSlipUpload() {
        return slipUpload;
    }

    public void setSlipUpload(String slipUpload) {
        this.slipUpload = slipUpload;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}