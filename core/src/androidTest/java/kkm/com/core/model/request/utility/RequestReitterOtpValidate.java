package kkm.com.core.model.request.utility;

import com.google.gson.annotations.SerializedName;

public class RequestReitterOtpValidate {

    @SerializedName("Type")
    private String type;

    @SerializedName("NUMBER")
    private String nUMBER;

    @SerializedName("remId")
    private String remId;

    @SerializedName("AMOUNT")
    private String aMOUNT;

    @SerializedName("AMOUNT_ALL")
    private String aMOUNTALL;

    @SerializedName("otc")
    private String otc;

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

    public String getOtc() {
        return otc;
    }

    public void setOtc(String otc) {
        this.otc = otc;
    }
}