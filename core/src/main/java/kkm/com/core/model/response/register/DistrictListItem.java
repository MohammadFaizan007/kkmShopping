package kkm.com.core.model.response.register;

import com.google.gson.annotations.SerializedName;

public class DistrictListItem {

    @SerializedName("districtName")
    private String districtName;

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @Override
    public String toString() {
        return
                "DistrictListItem{" +
                        "districtName = '" + districtName + '\'' +
                        "}";
    }
}