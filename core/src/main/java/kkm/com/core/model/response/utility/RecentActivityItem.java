package kkm.com.core.model.response.utility;

import com.google.gson.annotations.SerializedName;

public class RecentActivityItem {

    @SerializedName("Result")
    private String result;

    @SerializedName("Amount")
    private String amount;

    @SerializedName("MobileNo")
    private String mobileNo;

    @SerializedName("PaymentDate")
    private String paymentDate;

    @SerializedName("Error")
    private String error;

    @SerializedName("Operator")
    private String operator;

    @SerializedName("TransactionId")
    private String transactionId;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return
                "RecentActivityItem{" +
                        "result = '" + result + '\'' +
                        ",amount = '" + amount + '\'' +
                        ",mobileNo = '" + mobileNo + '\'' +
                        ",paymentDate = '" + paymentDate + '\'' +
                        ",error = '" + error + '\'' +
                        ",operator = '" + operator + '\'' +
                        ",transactionId = '" + transactionId + '\'' +
                        "}";
    }
}