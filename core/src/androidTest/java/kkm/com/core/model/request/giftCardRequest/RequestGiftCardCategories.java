package kkm.com.core.model.request.giftCardRequest;

import com.google.gson.annotations.SerializedName;

public class RequestGiftCardCategories {

    @SerializedName("Type")
    private String type;

    @SerializedName("Amount_All")
    private String amountAll;

    @SerializedName("Amount")
    private String amount;

    public void setType(String type) {
        this.type = type;
    }

    public void setAmountAll(String amountAll) {
        this.amountAll = amountAll;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}