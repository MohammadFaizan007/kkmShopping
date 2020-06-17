package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestWallet {

//    {"LoginId":"100101","RequestedAmount":"10","PaymentMode":"Cash","TransactionNo":"12345","PaymentDate":"15/03/2019","Fk_BankId":"1","RequestRemark":"Test"}


    @SerializedName("LoginId")
    private String loginId;

    @SerializedName("PaymentDate")
    private String PaymentDate;

    @SerializedName("Fk_BankId")
    private String Fk_BankId;

    @SerializedName("RequestRemark")
    private String RequestRemark;

    public void setPaymentDate(String paymentDate) {
        PaymentDate = paymentDate;
    }

    public void setFk_BankId(String fk_BankId) {
        Fk_BankId = fk_BankId;
    }

    public void setRequestRemark(String requestRemark) {
        RequestRemark = requestRemark;
    }

    @SerializedName("RequestedAmount")
    private String requestedAmount;

    @SerializedName("TransactionNo")
    private String transactionNo;

    @SerializedName("SlipUpload")
    private String slipUpload;

    @SerializedName("PaymentMode")
    private String paymentMode;


    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }


    public String getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(String requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getSlipUpload() {
        return slipUpload;
    }

    public void setSlipUpload(String slipUpload) {
        this.slipUpload = slipUpload;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
}