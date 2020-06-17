package kkm.com.core.model.response.register;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDistrict {

    @SerializedName("districtList")
    private List<DistrictListItem> districtList;

    @SerializedName("response")
    private String response;

    public List<DistrictListItem> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<DistrictListItem> districtList) {
        this.districtList = districtList;
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
                "ResponseDistrict{" +
                        "districtList = '" + districtList + '\'' +
                        ",response = '" + response + '\'' +
                        "}";
    }
}