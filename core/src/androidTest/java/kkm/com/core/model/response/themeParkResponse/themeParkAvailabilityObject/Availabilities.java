package kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityObject;

import com.google.gson.annotations.SerializedName;

public class Availabilities {

    @SerializedName("next_availabilities")
    private String nextAvailabilities;

    public String getNextAvailabilities() {
        return nextAvailabilities;
    }

    public void setNextAvailabilities(String nextAvailabilities) {
        this.nextAvailabilities = nextAvailabilities;
    }

    @Override
    public String toString() {
        return
                "Availabilities{" +
                        "next_availabilities = '" + nextAvailabilities + '\'' +
                        "}";
    }
}