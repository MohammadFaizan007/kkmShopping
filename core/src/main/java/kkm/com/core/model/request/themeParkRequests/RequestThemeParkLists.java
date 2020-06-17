package kkm.com.core.model.request.themeParkRequests;

import com.google.gson.annotations.SerializedName;

public class RequestThemeParkLists {

    @SerializedName("CategoryId")
    private String categoryId;

    @SerializedName("Type")
    private String type;

    @SerializedName("offset")
    private String offset;

    @SerializedName("AMOUNT")
    private String aMOUNT;

    @SerializedName("AMOUNT_ALL")
    private String aMOUNTALL;

    @SerializedName("limit")
    private String limit;

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public void setAMOUNT(String aMOUNT) {
        this.aMOUNT = aMOUNT;
    }

    public void setAMOUNTALL(String aMOUNTALL) {
        this.aMOUNTALL = aMOUNTALL;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}