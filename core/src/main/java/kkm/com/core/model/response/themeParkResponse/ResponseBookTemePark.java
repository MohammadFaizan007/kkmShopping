package kkm.com.core.model.response.themeParkResponse;

import com.google.gson.annotations.SerializedName;

public class ResponseBookTemePark {

    @SerializedName("date")
    private String date;

    @SerializedName("result")
    private String result;

    @SerializedName("trnxstatus")
    private String trnxstatus;

    @SerializedName("session")
    private String session;

    @SerializedName("transid")
    private String transid;

    @SerializedName("error")
    private String error;

    @SerializedName("addinfo")
    private String addinfo;

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getAddinfo() {
        return addinfo;
    }

    public void setAddinfo(String addinfo) {
        this.addinfo = addinfo;
    }
}