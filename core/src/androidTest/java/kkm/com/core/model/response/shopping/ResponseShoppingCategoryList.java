package kkm.com.core.model.response.shopping;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseShoppingCategoryList {

    @SerializedName("response")
    private String response;

    @SerializedName("categoryGroupWise")
    private List<CategoryGroupWiseItem> categoryGroupWise;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<CategoryGroupWiseItem> getCategoryGroupWise() {
        return categoryGroupWise;
    }

    public void setCategoryGroupWise(List<CategoryGroupWiseItem> categoryGroupWise) {
        this.categoryGroupWise = categoryGroupWise;
    }

    @Override
    public String toString() {
        return
                "ResponseShoppingCategoryList{" +
                        "response = '" + response + '\'' +
                        ",categoryGroupWise = '" + categoryGroupWise + '\'' +
                        "}";
    }
}