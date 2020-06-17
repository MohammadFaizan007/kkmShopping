package kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityArray;

import com.google.gson.annotations.SerializedName;

public class ScaleItem {

    @SerializedName("scale_max_participant")
    private String scaleMaxParticipant;

    @SerializedName("scale_min_participant")
    private String scaleMinParticipant;

    @SerializedName("price")
    private String price;

    @SerializedName("type")
    private String type;

    public String getScaleMaxParticipant() {
        return scaleMaxParticipant;
    }

    public void setScaleMaxParticipant(String scaleMaxParticipant) {
        this.scaleMaxParticipant = scaleMaxParticipant;
    }

    public String getScaleMinParticipant() {
        return scaleMinParticipant;
    }

    public void setScaleMinParticipant(String scaleMinParticipant) {
        this.scaleMinParticipant = scaleMinParticipant;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return
                "ScaleItem{" +
                        "scale_max_participant = '" + scaleMaxParticipant + '\'' +
                        ",scale_min_participant = '" + scaleMinParticipant + '\'' +
                        ",price = '" + price + '\'' +
                        ",type = '" + type + '\'' +
                        "}";
    }
}