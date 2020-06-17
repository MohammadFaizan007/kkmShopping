package kkm.com.core.model.response.themeParkResponse.themeParkDetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseThemeParkDetails {

    @SerializedName("DATE")
    private String date;

    @SerializedName("RESULT")
    private String result;

    @SerializedName("SESSION")
    private String session;

    @SerializedName("ERROR")
    private String error;

    @SerializedName("ADDINFO")
    private List<AddinfoItem> addinfo;

    public String getDate() {
        return date;
    }

    public String getResult() {
        return result;
    }

    public String getSession() {
        return session;
    }

    public String getError() {
        return error;
    }

    public List<AddinfoItem> getAddinfo() {
        return addinfo;
    }
}