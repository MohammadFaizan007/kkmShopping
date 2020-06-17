package kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityArray;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pricing {

    @SerializedName("categories")
    private List<CategoriesItem> categories;

    public List<CategoriesItem> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesItem> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return
                "Pricing{" +
                        "categories = '" + categories + '\'' +
                        "}";
    }
}