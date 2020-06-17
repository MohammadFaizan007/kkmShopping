package kkm.com.core.model.request.billpayment;

import com.google.gson.annotations.SerializedName;

public class RequestBillPayment {

    @SerializedName("NUMBER")
    private String nUMBER;

    @SerializedName("ACCOUNT")
    private String aCCOUNT;

    @SerializedName("AMOUNT_ALL")
    private String aMOUNTALL;

    @SerializedName("AMOUNT")
    private String aMOUNT;

    @SerializedName("Authenticator")
    private String authenticator;

    @SerializedName("Provider")
    private String provider;

    @SerializedName("FK_MemID")
    private String FK_MemID;

    @SerializedName("Type")
    private String Type;

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getFK_MemID() {
        return FK_MemID;
    }

    public void setFK_MemID(String FK_MemID) {
        this.FK_MemID = FK_MemID;
    }

    public String getNUMBER() {
        return nUMBER;
    }

    public void setNUMBER(String nUMBER) {
        this.nUMBER = nUMBER;
    }

    public String getACCOUNT() {
        return aCCOUNT;
    }

    public void setACCOUNT(String aCCOUNT) {
        this.aCCOUNT = aCCOUNT;
    }

    public String getAMOUNTALL() {
        return aMOUNTALL;
    }

    public void setAMOUNTALL(String aMOUNTALL) {
        this.aMOUNTALL = aMOUNTALL;
    }

    public String getAMOUNT() {
        return aMOUNT;
    }

    public void setAMOUNT(String aMOUNT) {
        this.aMOUNT = aMOUNT;
    }

    public String getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(String authenticator) {
        this.authenticator = authenticator;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}