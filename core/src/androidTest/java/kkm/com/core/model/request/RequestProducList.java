package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestProducList {

    @SerializedName("CategoryID")
    private String categoryID;

    @SerializedName("PriceRangeFrom")
    private String priceRangeFrom;

    @SerializedName("PriceRangeTo")
    private String priceRangeTo;

    @SerializedName("Parameter")
    private String parameter;

    @SerializedName("BrandID")
    private String brandID;

    @SerializedName("BrandArr")
    private List<Integer> brandArr;

    @SerializedName("ParentCategoryID")
    private String parentCategoryID;

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getPriceRangeFrom() {
        return priceRangeFrom;
    }

    public void setPriceRangeFrom(String priceRangeFrom) {
        this.priceRangeFrom = priceRangeFrom;
    }

    public String getPriceRangeTo() {
        return priceRangeTo;
    }

    public void setPriceRangeTo(String priceRangeTo) {
        this.priceRangeTo = priceRangeTo;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public List<Integer> getBrandArr() {
        return brandArr;
    }

    public void setBrandArr(List<Integer> brandArr) {
        this.brandArr = brandArr;
    }

    public String getParentCategoryID() {
        return parentCategoryID;
    }

    public void setParentCategoryID(String parentCategoryID) {
        this.parentCategoryID = parentCategoryID;
    }
}