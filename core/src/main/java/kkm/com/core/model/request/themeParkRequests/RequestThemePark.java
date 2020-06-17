package kkm.com.core.model.request.themeParkRequests;

import com.google.gson.annotations.SerializedName;

public class RequestThemePark {

    @SerializedName("Type")
    private String type;

    @SerializedName("NUMBER")
    private String nUMBER;

    @SerializedName("AMOUNT")
    private String aMOUNT;

    @SerializedName("AMOUNT_ALL")
    private String aMOUNTALL;

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

    @Override
    public String toString() {
        return
                "RequestThemePark{" +
                        "type = '" + type + '\'' +
                        ",nUMBER = '" + nUMBER + '\'' +
                        ",aMOUNT = '" + aMOUNT + '\'' +
                        ",aMOUNT_ALL = '" + aMOUNTALL + '\'' +
                        "}";
    }
}