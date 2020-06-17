package kkm.com.core.model.request.billpayment;

import com.google.gson.annotations.SerializedName;

public class RequestUpiLog {

    @SerializedName("Fk_MemId")
    private String fkMemId;

    @SerializedName("NUMBER")
    private String nUMBER;

    @SerializedName("SESSION")
    private String sESSION;

    @SerializedName("BillNumber")
    private String billNumber;

    @SerializedName("COMMENT")
    private String cOMMENT;

    @SerializedName("DATE")
    private String dATE;

    @SerializedName("Type")
    private String type;

    @SerializedName("TRANSID")
    private String tRANSID;


    @SerializedName("ERRMSG")
    private String eRRMSG;

    @SerializedName("AMOUNT")
    private String aMOUNT;

    @SerializedName("ERROR")
    private String eRROR;

    @SerializedName("RESULT")
    private String rESULT;

    @SerializedName("AUTHCODE")
    private String aUTHCODE;

    @SerializedName("TRNXSTATUS")
    private String tRNXSTATUS;

    public String getFkMemId() {
        return fkMemId;
    }

    public void setFkMemId(String fkMemId) {
        this.fkMemId = fkMemId;
    }

    public String getNUMBER() {
        return nUMBER;
    }

    public void setNUMBER(String nUMBER) {
        this.nUMBER = nUMBER;
    }

    public String getSESSION() {
        return sESSION;
    }

    public void setSESSION(String sESSION) {
        this.sESSION = sESSION;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getCOMMENT() {
        return cOMMENT;
    }

    public void setCOMMENT(String cOMMENT) {
        this.cOMMENT = cOMMENT;
    }

    public String getDATE() {
        return dATE;
    }

    public void setDATE(String dATE) {
        this.dATE = dATE;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTRANSID() {
        return tRANSID;
    }

    public void setTRANSID(String tRANSID) {
        this.tRANSID = tRANSID;
    }

    public String getERRMSG() {
        return eRRMSG;
    }

    public void setERRMSG(String eRRMSG) {
        this.eRRMSG = eRRMSG;
    }

    public String getAMOUNT() {
        return aMOUNT;
    }

    public void setAMOUNT(String aMOUNT) {
        this.aMOUNT = aMOUNT;
    }

    public String getERROR() {
        return eRROR;
    }

    public void setERROR(String eRROR) {
        this.eRROR = eRROR;
    }

    public String getRESULT() {
        return rESULT;
    }

    public void setRESULT(String rESULT) {
        this.rESULT = rESULT;
    }

    public String getAUTHCODE() {
        return aUTHCODE;
    }

    public void setAUTHCODE(String aUTHCODE) {
        this.aUTHCODE = aUTHCODE;
    }

    public String getTRNXSTATUS() {
        return tRNXSTATUS;
    }

    public void setTRNXSTATUS(String tRNXSTATUS) {
        this.tRNXSTATUS = tRNXSTATUS;
    }
}