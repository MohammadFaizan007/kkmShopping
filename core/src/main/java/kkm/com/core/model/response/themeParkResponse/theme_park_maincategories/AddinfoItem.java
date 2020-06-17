package kkm.com.core.model.response.themeParkResponse.theme_park_maincategories;

import com.google.gson.annotations.SerializedName;

public class AddinfoItem {

    @SerializedName("Name")
    private String name;

    @SerializedName("CategoryId")
    private String categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return
                "AddinfoItem{" +
                        "name = '" + name + '\'' +
                        ",categoryId = '" + categoryId + '\'' +
                        "}";
    }
}