package kkm.com.core.model.response.mlmDashboard;

import com.google.gson.annotations.SerializedName;

public class NewsndinfoItem {

    @SerializedName("time")
    private String time;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}