package kkm.com.core.model.response.utility;

import com.google.gson.annotations.SerializedName;

public class ResponseDthRecharge {

    @SerializedName("DATE")
    private String date;

    @SerializedName("RESULT")
    private String result;

    @SerializedName("AUTHCODE")
    private String authcode;

    @SerializedName("TRNXSTATUS")
    private String trnxstatus;

    @SerializedName("SESSION")
    private String session;

    @SerializedName("TRANSID")
    private String transid;

    @SerializedName("ERRMSG")
    private String errmsg;

    @SerializedName("ERROR")
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
                "ResponseDthRecharge{" +
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