package kkm.com.core.model.response.searchResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSearchItems {

    @SerializedName("recentlist")
    private List<RecentlistItem> recentlist;

    @SerializedName("keySearchlist")
    private List<KeySearchlistItem> keySearchlist;

    @SerializedName("trendinglist")
    private List<TrendinglistItem> trendinglist;

    @SerializedName("response")
    private String response;
    ;

    public List<RecentlistItem> getRecentlist() {
        return recentlist;
    }

    public List<KeySearchlistItem> getKeySearchlist() {
        return keySearchlist;
    }

    public String getResponse() {
        return response;
    }

    public List<TrendinglistItem> getTrendinglist() {
        return trendinglist;
    }
}