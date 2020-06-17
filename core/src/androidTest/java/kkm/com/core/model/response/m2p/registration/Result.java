package kkm.com.core.model.response.m2p.registration;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("result")
    private Result result;

    @SerializedName("exception")
    private String exception;

    @SerializedName("pagination")
    private String pagination;

    @SerializedName("sorCustomerId")
    private String sorCustomerId;

    @SerializedName("kycStatus")
    private String kycStatus;

    @SerializedName("kycExpiryDate")
    private String kycExpiryDate;

    @SerializedName("entityId")
    private String entityId;

    @SerializedName("kycRefNo")
    private String kycRefNo;

    @SerializedName("status")
    private String status;

    public Result getResult() {
        return result;
    }

    public String getException() {
        return exception;
    }

    public String getPagination() {
        return pagination;
    }

    public String getSorCustomerId() {
        return sorCustomerId;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public String getKycExpiryDate() {
        return kycExpiryDate;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getKycRefNo() {
        return kycRefNo;
    }

    public String getStatus() {
        return status;
    }
}