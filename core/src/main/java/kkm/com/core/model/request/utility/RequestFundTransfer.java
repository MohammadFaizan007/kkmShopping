package kkm.com.core.model.request.utility;

import com.google.gson.annotations.SerializedName;

public class RequestFundTransfer {

    @SerializedName("Type")
    private String type;

    @SerializedName("NUMBER")
    private String nUMBER;

    @SerializedName("benId")
    private String benId;

    @SerializedName("AMOUNT")
    private String aMOUNT;

    @SerializedName("AMOUNT_ALL")
    private String aMOUNTALL;

    @SerializedName("routingType")
    private String routingType;

    @SerializedName("FK_MemId")
    private String FK_MemId;

    @SerializedName("BeneficiaryName")
    private String BeneficiaryName;

    public void setBeneficiaryName(String beneficiaryName) {
        BeneficiaryName = beneficiaryName;
    }

    public void setFK_MemId(String FK_MemId) {
        this.FK_MemId = FK_MemId;
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

    public String getBenId() {
        return benId;
    }

    public void setBenId(String benId) {
        this.benId = benId;
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

    public String getRoutingType() {
        return routingType;
    }

    public void setRoutingType(String routingType) {
        this.routingType = routingType;
    }
}