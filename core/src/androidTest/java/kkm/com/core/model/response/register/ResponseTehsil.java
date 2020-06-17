package kkm.com.core.model.response.register;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTehsil {

    @SerializedName("tahsilNamelist")
    private List<TahsilNamelistItem> tahsilNamelist;

    @SerializedName("response")
    private String response;

    public List<TahsilNamelistItem> getTahsilNamelist() {
        return tahsilNamelist;
    }

    public void setTahsilNamelist(List<TahsilNamelistItem> tahsilNamelist) {
        this.tahsilNamelist = tahsilNamelist;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return
                "ResponseTehsil{" +
                        "tahsilNamelist = '" + tahsilNamelist + '\'' +
                        ",response = '" + response + '\'' +
                        "}";
    }
}