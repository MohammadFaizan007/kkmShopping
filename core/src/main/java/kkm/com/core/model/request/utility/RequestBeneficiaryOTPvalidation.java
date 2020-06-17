package kkm.com.core.model.request.utility;

import com.google.gson.annotations.SerializedName;

public class RequestBeneficiaryOTPvalidation {

    @SerializedName("Type")
    private String type;

    @SerializedName("NUMBER")
    private String nUMBER;

    @SerializedName("benId")
    private String benId;

    @SerializedName("remId")
    private String remId;

    @SerializedName("AMOUNT")
    private String aMOUNT;

    @SerializedName("AMOUNT_ALL")
    private String aMOUNTALL;

    @SerializedName("otc")
    private String otc;

    public void setType(String type) {
        this.type = type;
    }

    public void setNUMBER(String nUMBER) {
        this.nUMBER = nUMBER;
    }

    public void setBenId(String benId) {
        this.benId = benId;
    }

    public void setRemId(String remId) {
        this.remId = remId;
    }

    public void setAMOUNT(String aMOUNT) {
        this.aMOUNT = aMOUNT;
    }

    public void setAMOUNTALL(String aMOUNTALL) {
        this.aMOUNTALL = aMOUNTALL;
    }

    public void setOtc(String otc) {
        this.otc = otc;
    }
}