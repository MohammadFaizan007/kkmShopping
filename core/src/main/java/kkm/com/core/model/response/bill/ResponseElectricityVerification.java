package kkm.com.core.model.response.bill;

import com.google.gson.annotations.SerializedName;

public class ResponseElectricityVerification {

    @SerializedName("date")
    private String date;

    @SerializedName("result")
    private String result;

    @SerializedName("authcode")
    private String authcode;

    @SerializedName("trnxstatus")
    private String trnxstatus;

    @SerializedName("session")
    private String session;

    @SerializedName("transid")
    private String transid;

    @SerializedName("price")
    private String price;

    @SerializedName("errmsg")
    private String errmsg;

    @SerializedName("addInfo")
    private String addInfo;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private Object message;

    @SerializedName("status")
    private Object status;

    public String getDate() {
        return date;
    }

    public String getResult() {
        return result;
    }

    public String getAuthcode() {
        return authcode;
    }

    public String getTrnxstatus() {
        return trnxstatus;
    }

    public String getSession() {
        return session;
    }

    public String getTransid() {
        return transid;
    }

    public String getPrice() {
        return price;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public String getError() {
        return error;
    }

    public Object getMessage() {
        return message;
    }

    public Object getStatus() {
        return status;
    }
}