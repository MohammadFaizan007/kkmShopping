package kkm.com.core.model.response.team;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDownline {

    @SerializedName("response")
    private String response;

    @SerializedName("downlineList")
    private List<DownlineListItem> downlineList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<DownlineListItem> getDownlineList() {
        return downlineList;
    }

    public void setDownlineList(List<DownlineListItem> downlineList) {
        this.downlineList = downlineList;
    }
}