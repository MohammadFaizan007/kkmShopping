package kkm.com.core.model.request.billpayment;
import com.google.gson.annotations.SerializedName;

public class RequestElectricityVerification {

    @SerializedName("Type")
    private String type;

    @SerializedName("ACCOUNT")
    private String aCCOUNT;

    @SerializedName("NUMBER")
    private String nUMBER;

    @SerializedName("AMOUNT")
    private String aMOUNT;

    @SerializedName("AMOUNT_ALL")
    private String aMOUNTALL;

    @SerializedName("FK_MemID")
    private String fKMemID;

    @SerializedName("Authenticator")
    private String authenticator;

    @SerializedName("Provider")
    private String provider;

    public void setType(String type) {
        this.type = type;
    }

    public void setACCOUNT(String aCCOUNT) {
        this.aCCOUNT = aCCOUNT;
    }

    public void setNUMBER(String nUMBER) {
        this.nUMBER = nUMBER;
    }

    public void setAMOUNT(String aMOUNT) {
        this.aMOUNT = aMOUNT;
    }

    public void setAMOUNTALL(String aMOUNTALL) {
        this.aMOUNTALL = aMOUNTALL;
    }

    public void setFKMemID(String fKMemID) {
        this.fKMemID = fKMemID;
    }

    public void setAuthenticator(String authenticator) {
        this.authenticator = authenticator;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}