package kkm.com.core.model.response.giftCardResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGiftCardcategory {

    @SerializedName("date")
    private String date;

    @SerializedName("result")
    private String result;

    @SerializedName("session")
    private String session;

    @SerializedName("error")
    private String error;

    @SerializedName("addinfo")
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