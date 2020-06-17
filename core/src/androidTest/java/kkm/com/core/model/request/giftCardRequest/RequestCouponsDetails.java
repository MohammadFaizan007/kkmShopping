package kkm.com.core.model.request.giftCardRequest;

import com.google.gson.annotations.SerializedName;

public class RequestCouponsDetails {

    @SerializedName("Type")
    private String type;

    @SerializedName("Amount_All")
    private String amountAll;

    @SerializedName("Product_id")
    private String productId;

    @SerializedName("Amount")
    private String amount;

    public void setType(String type) {
        this.type = type;
    }

    public void setAmountAll(String amountAll) {
        this.amountAll = amountAll;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}