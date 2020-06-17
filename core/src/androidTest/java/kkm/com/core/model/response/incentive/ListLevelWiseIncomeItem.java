package kkm.com.core.model.response.incentive;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListLevelWiseIncomeItem implements Serializable {

    @SerializedName("income")
    private String income;

    @SerializedName("business")
    private String business;

    @SerializedName("level")
    private String level;

    @SerializedName("levelId")
    private String levelId;

    @SerializedName("orders")
    private String orders;

    public String getIncome() {
        return income;
    }

    public String getBusiness() {
        return business;
    }

    public String getLevel() {
        return level;
    }

    public String getLevelId() {
        return levelId;
    }

    public String getOrders() {
        return orders;
    }
}