package kkm.com.core.model.response.utility;

import com.google.gson.annotations.SerializedName;

public class ResponseBenficiarylist {

    @SerializedName("remitterId")
    private String remitterId;

    @SerializedName("beneficiaryAccountNo")
    private String beneficiaryAccountNo;

    @SerializedName("beneficiaryIFSC")
    private String beneficiaryIFSC;

    @SerializedName("beneficiaryBankName")
    private String beneficiaryBankName;

    @SerializedName("beneficiaryLastName")
    private String beneficiaryLastName;

    @SerializedName("beneficiaryFirstName")
    private String beneficiaryFirstName;

    @SerializedName("beneficiaryMobileNo")
    private String beneficiaryMobileNo;

    @SerializedName("beneficiaryId")
    private String beneficiaryId;

    @SerializedName("status")
    private String status;

    public String getRemitterId() {
        return remitterId;
    }

    public void setRemitterId(String remitterId) {
        this.remitterId = remitterId;
    }

    public String getBeneficiaryAccountNo() {
        return beneficiaryAccountNo;
    }

    public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
        this.beneficiaryAccountNo = beneficiaryAccountNo;
    }

    public String getBeneficiaryIFSC() {
        return beneficiaryIFSC;
    }

    public void setBeneficiaryIFSC(String beneficiaryIFSC) {
        this.beneficiaryIFSC = beneficiaryIFSC;
    }

    public String getBeneficiaryBankName() {
        return beneficiaryBankName;
    }

    public void setBeneficiaryBankName(String beneficiaryBankName) {
        this.beneficiaryBankName = beneficiaryBankName;
    }

    public String getBeneficiaryLastName() {
        return beneficiaryLastName;
    }

    public void setBeneficiaryLastName(String beneficiaryLastName) {
        this.beneficiaryLastName = beneficiaryLastName;
    }

    public String getBeneficiaryFirstName() {
        return beneficiaryFirstName;
    }

    public void setBeneficiaryFirstName(String beneficiaryFirstName) {
        this.beneficiaryFirstName = beneficiaryFirstName;
    }

    public String getBeneficiaryMobileNo() {
        return beneficiaryMobileNo;
    }

    public void setBeneficiaryMobileNo(String beneficiaryMobileNo) {
        this.beneficiaryMobileNo = beneficiaryMobileNo;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}