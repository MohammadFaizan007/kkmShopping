package kkm.com.core.model.response.bill;

import com.google.gson.annotations.SerializedName;

public class ResponseGasVerification {

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

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    @SerializedName("addinfo")
    private String addinfo;

    @SerializedName("status")
    private String status;

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

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getAddinfo() {
        return addinfo;
    }

    public String getStatus() {
        return status;
    }
}