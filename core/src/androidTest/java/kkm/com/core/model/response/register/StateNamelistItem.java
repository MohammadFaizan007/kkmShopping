package kkm.com.core.model.response.register;

import com.google.gson.annotations.SerializedName;

public class StateNamelistItem {

    @SerializedName("stateName")
    private String stateName;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String toString() {
        return
                "StateNamelistItem{" +
                        "stateName = '" + stateName + '\'' +
                        "}";
    }
}