package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

public class FundTransferLogItem {

    @SerializedName("transAmount")
    private String transAmount;

    @SerializedName("beneficiaryName")
    private String beneficiaryName;

    @SerializedName("transdate")
    private String transdate;

    @SerializedName("transNo")
    private String transNo;

    @SerializedName("remarks")
    private String remarks;

    @SerializedName("transStatus")
    private String transStatus;

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getTransdate() {
        return transdate;
    }

    public void setTransdate(String transdate) {
        this.transdate = transdate;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }
}