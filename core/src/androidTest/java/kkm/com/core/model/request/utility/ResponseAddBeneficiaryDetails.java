package kkm.com.core.model.request.utility;

import com.google.gson.annotations.SerializedName;

public class ResponseAddBeneficiaryDetails {

    @SerializedName("BeneficiaryMobileNo")
    private String beneficiaryMobileNo;

    @SerializedName("BeneficiaryBankName")
    private String beneficiaryBankName;

    @SerializedName("BeneficiaryIFSC")
    private String beneficiaryIFSC;

    @SerializedName("BeneficiaryLastName")
    private String beneficiaryLastName;

    @SerializedName("BeneficiaryAccountNo")
    private String beneficiaryAccountNo;

    @SerializedName("BeneficiaryFirstName")
    private String beneficiaryFirstName;

    @SerializedName("BeneficiaryId")
    private String beneficiaryId;

    @SerializedName("RemitterId")
    private String remitterId;

    public void setBeneficiaryMobileNo(String beneficiaryMobileNo) {
        this.beneficiaryMobileNo = beneficiaryMobileNo;
    }

    public void setBeneficiaryBankName(String beneficiaryBankName) {
        this.beneficiaryBankName = beneficiaryBankName;
    }

    public void setBeneficiaryIFSC(String beneficiaryIFSC) {
        this.beneficiaryIFSC = beneficiaryIFSC;
    }

    public void setBeneficiaryLastName(String beneficiaryLastName) {
        this.beneficiaryLastName = beneficiaryLastName;
    }

    public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
        this.beneficiaryAccountNo = beneficiaryAccountNo;
    }

    public void setBeneficiaryFirstName(String beneficiaryFirstName) {
        this.beneficiaryFirstName = beneficiaryFirstName;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public void setRemitterId(String remitterId) {
        this.remitterId = remitterId;
    }
}