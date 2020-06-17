package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class SubCategoryListItem {

    @SerializedName("images")
    private String images;

    @SerializedName("parentCategoryID")
    private String parentCategoryID;

    @SerializedName("categoryName")
    private String categoryName;

    @SerializedName("categoryId")
    private String categoryId;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getParentCategoryID() {
        return parentCategoryID;
    }

    public void setParentCategoryID(String parentCategoryID) {
        this.parentCategoryID = parentCategoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


}