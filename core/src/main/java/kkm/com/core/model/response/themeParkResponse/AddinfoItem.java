package kkm.com.core.model.response.themeParkResponse;

import com.google.gson.annotations.SerializedName;

public class AddinfoItem {

    @SerializedName("name")
    private String name;

    @SerializedName("categoryId")
    private String categoryId;

    public String getName() {
        return name;
    }

    public String getCategoryId() {
        return categoryId;
    }
}