package kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityArray;

import com.google.gson.annotations.SerializedName;

public class Availabilities {

    @SerializedName("visit_date")
    private String visitDate;

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    @Override
    public String toString() {
        return
                "Availabilities{" +
                        "visit_date = '" + visitDate + '\'' +
                        "}";
    }
}