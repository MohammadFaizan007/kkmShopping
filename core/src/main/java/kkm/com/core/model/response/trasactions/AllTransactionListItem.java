package kkm.com.core.model.response.trasactions;

import com.google.gson.annotations.SerializedName;

public class AllTransactionListItem {

    @SerializedName("Amount")
    private String amount;

    @SerializedName("TransactionStatus")
    private String transactionStatus;

    @SerializedName("Session")
    private String session;

    @SerializedName("MobileNo")
    private String mobileNo;

    @SerializedName("Error")
    private String error;

    @SerializedName("Type")
    private String type;

    @SerializedName("Operator")
    private String operator;

    @SerializedName("TransactionId")
    private String transactionId;

    @SerializedName("Result")
    private String result;

    @SerializedName("ActionType")
    private String actionType;

    @SerializedName("PaymentDate")
    private String paymentDate;

    @SerializedName("DrAmount")
    private String drAmount;

    @SerializedName("CrAmount")
    private String crAmount;

    @SerializedName("Remarks")
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