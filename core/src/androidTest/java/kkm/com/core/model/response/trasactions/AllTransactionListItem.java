package kkm.com.core.model.response.trasactions;

import com.google.gson.annotations.SerializedName;

public class AllTransactionListItem {

    @SerializedName("amount")
    private String amount;

    @SerializedName("transactionStatus")
    private String transactionStatus;

    @SerializedName("session")
    private String session;

    @SerializedName("mobileNo")
    private String mobileNo;

    @SerializedName("error")
    private String error;

    @SerializedName("type")
    private String type;

    @SerializedName("operator")
    private String operator;

    @SerializedName("transactionId")
    private String transactionId;

    @SerializedName("result")
    private String result;

    @SerializedName("actionType")
    private String actionType;

    @SerializedName("paymentDate")
    private String paymentDate;

    @SerializedName("drAmount")
    private String drAmount;

    @SerializedName("crAmount")
    private String crAmount;

    @SerializedName("remarks")
    private String remarks;

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSession() {
        return session;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setDrAmount(String drAmount) {
        this.drAmount = drAmount;
    }

    public String getDrAmount() {
        return drAmount;
    }

    public void setCrAmount(String crAmount) {
        this.crAmount = crAmount;
    }

    public String getCrAmount() {
        return crAmount;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }
}