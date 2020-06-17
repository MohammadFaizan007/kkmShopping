package kkm.com.core.model.response.utility;

import com.google.gson.annotations.SerializedName;

public class ResponsePrepaidRecharge {

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

    @SerializedName("errmsg")
    private String errmsg;

    @SerializedName("error")
    private String error;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

    public String getTrnxstatus() {
        return trnxstatus;
    }

    public void setTrnxstatus(String trnxstatus) {
        this.trnxstatus = trnxstatus;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return
                "ResponsePrepaidRecharge{" +
                        "date = '" + date + '\'' +
                        ",result = '" + result + '\'' +
                        ",authcode = '" + authcode + '\'' +
                        ",trnxstatus = '" + trnxstatus + '\'' +
                        ",session = '" + session + '\'' +
                        ",transid = '" + transid + '\'' +
                        ",errmsg = '" + errmsg + '\'' +
                        ",error = '" + error + '\'' +
                        "}";
    }
}