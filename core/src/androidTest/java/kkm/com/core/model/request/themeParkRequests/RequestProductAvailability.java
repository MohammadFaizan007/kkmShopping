package kkm.com.core.model.request.themeParkRequests;

import com.google.gson.annotations.SerializedName;

public class RequestProductAvailability {

    @SerializedName("Type")
    private String type;

    @SerializedName("AMOUNT")
    private String aMOUNT;

    @SerializedName("AMOUNT_ALL")
    private String aMOUNTALL;

    @SerializedName("ProductId")
    private String productId;

    @SerializedName("DateOfTour")
    private String dateOfTour;

    public void setType(String type) {
        this.type = type;
    }

    public void setAMOUNT(String aMOUNT) {
        this.aMOUNT = aMOUNT;
    }

    public void setAMOUNTALL(String aMOUNTALL) {
        this.aMOUNTALL = aMOUNTALL;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setDateOfTour(String dateOfTour) {
        this.dateOfTour = dateOfTour;
    }
}