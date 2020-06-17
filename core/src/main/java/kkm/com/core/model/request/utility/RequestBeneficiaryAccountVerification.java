package kkm.com.core.model.request.utility;

import com.google.gson.annotations.SerializedName;

public class RequestBeneficiaryAccountVerification {

    @SerializedName("Type")
    private String type;

    @SerializedName("NUMBER")
    private String nUMBER;

    @SerializedName("benIFSC")
    private String benIFSC;

    @SerializedName("AMOUNT")
    private String aMOUNT;

    @SerializedName("AMOUNT_ALL")
    private String aMOUNTALL;

    @SerializedName("benAccount")
    private String benAccount;

    public void setType(String type) {
        this.type = type;
    }

    public void setNUMBER(String nUMBER) {
        this.nUMBER = nUMBER;
    }

    public void setBenIFSC(String benIFSC) {
        this.benIFSC = benIFSC;
    }

    public void setAMOUNT(String aMOUNT) {
        this.aMOUNT = aMOUNT;
    }

    public void setAMOUNTALL(String aMOUNTALL) {
        this.aMOUNTALL = aMOUNTALL;
    }

    public void setBenAccount(String benAccount) {
        this.benAccount = benAccount;
    }
}