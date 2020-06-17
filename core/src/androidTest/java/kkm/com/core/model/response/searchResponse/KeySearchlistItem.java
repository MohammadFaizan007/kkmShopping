package kkm.com.core.model.response.searchResponse;

import com.google.gson.annotations.SerializedName;

public class KeySearchlistItem {

    @SerializedName("className")
    private String className;

    @SerializedName("categoryName")
    private String categoryName;

    @SerializedName("categoryId")
    private String categoryId;

    public String getClassName() {
        return className;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }
}