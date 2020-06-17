package kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityArray;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesItem {

    @SerializedName("max_age")
    private String maxAge;

    @SerializedName("addon")
    private boolean addon;

    @SerializedName("name")
    private String name;

    @SerializedName("scale")
    private List<ScaleItem> scale;

    @SerializedName("stand_alone")
    private boolean standAlone;

    @SerializedName("id")
    private String id;

    @SerializedName("min_age")
    private String minAge;

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    public boolean isAddon() {
        return addon;
    }

    public void setAddon(boolean addon) {
        this.addon = addon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScaleItem> getScale() {
        return scale;
    }

    public void setScale(List<ScaleItem> scale) {
        this.scale = scale;
    }

    public boolean isStandAlone() {
        return standAlone;
    }

    public void setStandAlone(boolean standAlone) {
        this.standAlone = standAlone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMinAge() {
        return minAge;
    }

    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    @Override
    public String toString() {
        return
                "CategoriesItem{" +
                        "max_age = '" + maxAge + '\'' +
                        ",addon = '" + addon + '\'' +
                        ",name = '" + name + '\'' +
                        ",scale = '" + scale + '\'' +
                        ",stand_alone = '" + standAlone + '\'' +
                        ",id = '" + id + '\'' +
                        ",min_age = '" + minAge + '\'' +
                        "}";
    }
}