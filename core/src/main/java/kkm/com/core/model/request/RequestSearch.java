package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestSearch {

    @SerializedName("CreatedBy")
    private String createdBy;

    @SerializedName("SearchString")
    private String searchString;

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}