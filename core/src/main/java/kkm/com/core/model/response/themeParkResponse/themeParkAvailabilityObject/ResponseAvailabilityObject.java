package kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityObject;

import com.google.gson.annotations.SerializedName;

public class ResponseAvailabilityObject {

    @SerializedName("DATA")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return
                "ResponseAvailabilityObject{" +
                        "data = '" + data + '\'' +
                        "}";
    }
}