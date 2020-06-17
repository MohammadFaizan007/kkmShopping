package kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityObject;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("availabilities")
    private Availabilities availabilities;

    @SerializedName("error")
    private String error;

    public Availabilities getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(Availabilities availabilities) {
        this.availabilities = availabilities;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "availabilities = '" + availabilities + '\'' +
                        ",error = '" + error + '\'' +
                        "}";
    }
}