package kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityArray;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("availabilities")
    private Availabilities availabilities;

    @SerializedName("tour_options")
    private List<TourOptionsItem> tourOptions;

    public Availabilities getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(Availabilities availabilities) {
        this.availabilities = availabilities;
    }

    public List<TourOptionsItem> getTourOptions() {
        return tourOptions;
    }

    public void setTourOptions(List<TourOptionsItem> tourOptions) {
        this.tourOptions = tourOptions;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "availabilities = '" + availabilities + '\'' +
                        ",tour_options = '" + tourOptions + '\'' +
                        "}";
    }
}