package kkm.com.core.model.response.utility;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseRecentRecharges {

    @SerializedName("recentActivity")
    private List<RecentActivityItem> recentActivity;

    @SerializedName("response")
    private String response;

    public List<RecentActivityItem> getRecentActivity() {
        return recentActivity;
    }

    public void setRecentActivity(List<RecentActivityItem> recentActivity) {
        this.recentActivity = recentActivity;
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
                "ResponseRecentRecharges{" +
                        "recentActivity = '" + recentActivity + '\'' +
                        ",response = '" + response + '\'' +
                        "}";
    }
}