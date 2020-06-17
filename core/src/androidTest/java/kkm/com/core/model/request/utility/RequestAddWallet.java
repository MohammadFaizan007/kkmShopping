package kkm.com.core.model.request.utility;

import com.google.gson.annotations.SerializedName;

public class RequestAddWallet {

    @SerializedName("Narration")
    private String narration;

    @SerializedName("BeneficiaryName")
    private String beneficiaryName;

    @SerializedName("CreditAmount")
    private String creditAmount;

    @SerializedName("SESSION")
    private String sESSION;

    @SerializedName("Fk_MemberId")
    private String fkMemberId;

    @SerializedName("DATE")
    private String dATE;

    @SerializedName("Remarks")
    private String remarks;

    @SerializedName("TransDate")
    private String transDate;

    @SerializedName("TransNo")
    private String transNo;

    @SerializedName("TransStatus")
    private String transStatus;

    @SerializedName("ERROR")
    private String eRROR;

    @SerializedName("TransAmount")
    private String transAmount;

    @SerializedName("RESULT")
    private String rESULT;

    @SerializedName("DebitAmount")
    private String debitAmount;

    @SerializedName("RemittersMobileNo")
    private String remittersMobileNo;

    @SerializedName("beneficiaryid")
    private String beneficiaryid;

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getSESSION() {
        return sESSION;
    }

    public void setSESSION(String sESSION) {
        this.sESSION = sESSION;
    }

    public String getFkMemberId() {
        return fkMemberId;
    }

    public void setFkMemberId(String fkMemberId) {
        this.fkMemberId = fkMemberId;
    }

    public String getDATE() {
        return dATE;
    }

    public void setDATE(String dATE) {
        this.dATE = dATE;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getERROR() {
        return eRROR;
    }

    public void setERROR(String eRROR) {
        this.eRROR = eRROR;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getRESULT() {
        return rESULT;
    }

    public void setRESULT(String rESULT) {
        this.rESULT = rESULT;
    }

    public String getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(String debitAmount) {
        this.debitAmount = debitAmount;
    }

    public String getRemittersMobileNo() {
        return remittersMobileNo;
    }

    public void setRemittersMobileNo(String remittersMobileNo) {
        this.remittersMobileNo = remittersMobileNo;
    }

    public String getBeneficiaryid() {
        return beneficiaryid;
    }

    public void setBeneficiaryid(String beneficiaryid) {
        this.beneficiaryid = beneficiaryid;
    }
}