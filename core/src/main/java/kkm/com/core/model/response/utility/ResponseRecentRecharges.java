package kkm.com.core.model.response.utility;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseRecentRecharges {

    @SerializedName("RecentActivity")
    private List<RecentActivityItem> recentActivity;

    @SerializedName("Response")
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


}