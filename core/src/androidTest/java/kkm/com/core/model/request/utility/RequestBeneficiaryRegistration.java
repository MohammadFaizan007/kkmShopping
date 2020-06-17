package kkm.com.core.model.request.utility;

import com.google.gson.annotations.SerializedName;

public class RequestBeneficiaryRegistration {

    @SerializedName("lName")
    private String lName;

    @SerializedName("Type")
    private String type;

    @SerializedName("NUMBER")
    private String nUMBER;

    @SerializedName("remId")
    private String remId;

    @SerializedName("fName")
    private String fName;

    @SerializedName("benIFSC")
    private String benIFSC;

    @SerializedName("AMOUNT")
    private String aMOUNT;

    @SerializedName("AMOUNT_ALL")
    private String aMOUNTALL;

    @SerializedName("benAccount")
    private String benAccount;

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNUMBER() {
        return nUMBER;
    }

    public void setNUMBER(String nUMBER) {
        this.nUMBER = nUMBER;
    }

    public String getRemId() {
        return remId;
    }

    public void setRemId(String remId) {
        this.remId = remId;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getBenIFSC() {
        return benIFSC;
    }

    public void setBenIFSC(String benIFSC) {
        this.benIFSC = benIFSC;
    }

    public String getAMOUNT() {
        return aMOUNT;
    }

    public void setAMOUNT(String aMOUNT) {
        this.aMOUNT = aMOUNT;
    }

    public String getAMOUNTALL() {
        return aMOUNTALL;
    }

    public void setAMOUNTALL(String aMOUNTALL) {
        this.aMOUNTALL = aMOUNTALL;
    }

    public String getBenAccount() {
        return benAccount;
    }

    public void setBenAccount(String benAccount) {
        this.benAccount = benAccount;
    }
}