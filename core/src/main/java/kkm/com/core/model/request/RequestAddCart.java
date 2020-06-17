package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestAddCart {

    @SerializedName("SrNo")
    private String srNo;

    @SerializedName("ProductAmt")
    private String productAmt;

    @SerializedName("Pk_ProductDetailId")
    private String pkProductDetailId;

    @SerializedName("ProductQty")
    private String productQty;

    @SerializedName("ProductId")
    private String productId;

    @SerializedName("AddedBy")
    private String addedBy;

    @SerializedName("SizeID")
    private String sizeID;

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getProductAmt() {
        return productAmt;
    }

    public void setProductAmt(String productAmt) {
        this.productAmt = productAmt;
    }

    public String getPkProductDetailId() {
        return pkProductDetailId;
    }

    public void setPkProductDetailId(String pkProductDetailId) {
        this.pkProductDetailId = pkProductDetailId;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getSizeID() {
        return sizeID;
    }

    public void setSizeID(String sizeID) {
        this.sizeID = sizeID;
    }

    @Override
    public String toString() {
        return
                "RequestAddCart{" +
                        "srNo = '" + srNo + '\'' +
                        ",productAmt = '" + productAmt + '\'' +
                        ",pk_ProductDetailId = '" + pkProductDetailId + '\'' +
                        ",productQty = '" + productQty + '\'' +
                        ",productId = '" + productId + '\'' +
                        ",addedBy = '" + addedBy + '\'' +
                        ",sizeID = '" + sizeID + '\'' +
                        "}";
    }
}