package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestCustomerVerification {

    @SerializedName("Type")
    private String type;

    @SerializedName("NUMBER")
    private String nUMBER;

    @SerializedName("AMOUNT_ALL")
    private String aMOUNTALL;

    @SerializedName("Amount")
    private String amount;

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

    public String getAMOUNTALL() {
        return aMOUNTALL;
    }

    public void setAMOUNTALL(String aMOUNTALL) {
        this.aMOUNTALL = aMOUNTALL;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return
                "RequestCustomerVerification{" +
                        "type = '" + type + '\'' +
                        ",nUMBER = '" + nUMBER + '\'' +
                        ",aMOUNT_ALL = '" + aMOUNTALL + '\'' +
                        ",amount = '" + amount + '\'' +
                        "}";
    }
}