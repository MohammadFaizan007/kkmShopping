package kkm.com.core.model.response.bill;

import com.google.gson.annotations.SerializedName;

public class ResponseWaterVerification {

    @SerializedName("date")
    private String date;

    @SerializedName("trnxstatus")
    private String trnxstatus;

    @SerializedName("referenceno")
    private String referenceno;

    @SerializedName("session")
    private String session;

    @SerializedName("transid")
    private String transid;

    @SerializedName("errmsg")
    private String errmsg;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private String result;

    @SerializedName("authcode")
    private String authcode;

    @SerializedName("billerconfirmationnumber")
    private String billerconfirmationnumber;

    @SerializedName("price")
    private String price;

    @SerializedName("addinfo")
    private String addinfo;

    @SerializedName("status")
    private String status;

    public String getDate() {
        return date;
    }

    public String getTrnxstatus() {
        return trnxstatus;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public String getSession() {
        return session;
    }

    public String getTransid() {
        return transid;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }

    public String getAuthcode() {
        return authcode;
    }

    public String getBillerconfirmationnumber() {
        return billerconfirmationnumber;
    }

    public String getPrice() {
        return price;
    }

    public String getAddinfo() {
        return addinfo;
    }

    public String getStatus() {
        return status;
    }
}