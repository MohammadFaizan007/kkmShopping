package kkm.com.core.model.request.giftCardRequest;

import com.google.gson.annotations.SerializedName;

public class RequestGiftCoupons {

    @SerializedName("Type")
    private String type;

    @SerializedName("Amount_All")
    private String amountAll;

    @SerializedName("Amount")
    private String amount;

    @SerializedName("Category_id")
    private String categoryId;

    public void setType(String type) {
        this.type = type;
    }

    public void setAmountAll(String amountAll) {
        this.amountAll = amountAll;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}