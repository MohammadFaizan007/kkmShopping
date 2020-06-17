package kkm.com.core.model.response.register;

import com.google.gson.annotations.SerializedName;

public class TehsilresponseItem {

    @SerializedName("pK_TehsilId")
    private String pKTehsilId;

    @SerializedName("districtName")
    private String districtName;

    @SerializedName("stateName")
    private String stateName;

    @SerializedName("tehsilName")
    private String tehsilName;

    public String getPKTehsilId() {
        return pKTehsilId;
    }

    public void setPKTehsilId(String pKTehsilId) {
        this.pKTehsilId = pKTehsilId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getTehsilName() {
        return tehsilName;
    }

    public void setTehsilName(String tehsilName) {
        this.tehsilName = tehsilName;
    }
}