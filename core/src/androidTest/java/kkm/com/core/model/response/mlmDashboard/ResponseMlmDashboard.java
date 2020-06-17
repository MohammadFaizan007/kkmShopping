package kkm.com.core.model.response.mlmDashboard;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMlmDashboard {

    @SerializedName("myOrder")
    private String myOrder;

    @SerializedName("myUsedValue")
    private String myUsedValue;

    @SerializedName("response")
    private String response;

    @SerializedName("firstLoginDate")
    private String firstLoginDate;

    @SerializedName("dashboard")
    private List<DashboardItem> dashboard;

    public String getMyOrder() {
        return myOrder;
    }

    public String getMyUsedValue() {
        return myUsedValue;
    }

    public String getResponse() {
        return response;
    }

    public String getFirstLoginDate() {
        return firstLoginDate;
    }

    public List<DashboardItem> getDashboard() {
        return dashboard;
    }
}