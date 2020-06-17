package kkm.com.core.model.response.shopping;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubCategoryItem implements Serializable {

    @SerializedName("images")
    private String images;

    @SerializedName("fK_CategoryId")
    private String fKCategoryId;

    @SerializedName("pK_CategoryId")
    private String pKCategoryId;

    @SerializedName("categoryName")
    private String categoryName;

    public void setImages(String images) {
        this.images = images;
    }

    public String getImages() {
        return images;
    }

    public void setFKCategoryId(String fKCategoryId) {
        this.fKCategoryId = fKCategoryId;
    }

    public String getFKCategoryId() {
        return fKCategoryId;
    }

    public void setPKCategoryId(String pKCategoryId) {
        this.pKCategoryId = pKCategoryId;
    }

    public String getPKCategoryId() {
        return pKCategoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String toString() {
        return
                "SubCategoryItem{" +
                        "images = '" + images + '\'' +
                        ",fK_CategoryId = '" + fKCategoryId + '\'' +
                        ",pK_CategoryId = '" + pKCategoryId + '\'' +
                        ",categoryName = '" + categoryName + '\'' +
                        "}";
    }
}