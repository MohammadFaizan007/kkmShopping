package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

public class GetPayoutRequestDetailsItem {

    @SerializedName("paymentMode")
    private String paymentMode;

    @SerializedName("approvedBy")
    private String approvedBy;

    @SerializedName("requestNo")
    private String requestNo;

    public String getPaymentMode() {
        return paymentMode;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public String getRequestNo() {
        return requestNo;
    }

    @SerializedName("approvalStatus")
    private String approvalStatus;

    @SerializedName("fK_MemId")
    private String fKMemId;

    @SerializedName("loginId")
    private String loginId;

    @SerializedName("approvalDate")
    private String approvalDate;

    @SerializedName("displayName")
    private String displayName;

    @SerializedName("memberAccNo")
    private String memberAccNo;

    @SerializedName("memberBankName")
    private String memberBankName;

    @SerializedName("memberBranch")
    private String memberBranch;

    @SerializedName("requestedAmount")
    private String requestedAmount;

    @SerializedName("requestedDate")
    private String requestedDate;

    @SerializedName("ifscCode")
    private String ifscCode;

    @SerializedName("pK_RequestId")
    private String pKRequestId;

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setFKMemId(String fKMemId) {
        this.fKMemId = fKMemId;
    }

    public String getFKMemId() {
        return fKMemId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setMemberAccNo(String memberAccNo) {
        this.memberAccNo = memberAccNo;
    }

    public String getMemberAccNo() {
        return memberAccNo;
    }

    public void setMemberBankName(String memberBankName) {
        this.memberBankName = memberBankName;
    }

    public String getMemberBankName() {
        return memberBankName;
    }

    public void setMemberBranch(String memberBranch) {
        this.memberBranch = memberBranch;
    }

    public String getMemberBranch() {
        return memberBranch;
    }

    public void setRequestedAmount(String requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setPKRequestId(String pKRequestId) {
        this.pKRequestId = pKRequestId;
    }

    public String getPKRequestId() {
        return pKRequestId;
    }

    @Override
    public String toString() {
        return
                "GetPayoutRequestDetailsItem{" +
                        "approvalStatus = '" + approvalStatus + '\'' +
                        ",fK_MemId = '" + fKMemId + '\'' +
                        ",loginId = '" + loginId + '\'' +
                        ",approvalDate = '" + approvalDate + '\'' +
                        ",displayName = '" + displayName + '\'' +
                        ",memberAccNo = '" + memberAccNo + '\'' +
                        ",memberBankName = '" + memberBankName + '\'' +
                        ",memberBranch = '" + memberBranch + '\'' +
                        ",requestedAmount = '" + requestedAmount + '\'' +
                        ",requestedDate = '" + requestedDate + '\'' +
                        ",ifscCode = '" + ifscCode + '\'' +
                        ",pK_RequestId = '" + pKRequestId + '\'' +
                        "}";
    }
}