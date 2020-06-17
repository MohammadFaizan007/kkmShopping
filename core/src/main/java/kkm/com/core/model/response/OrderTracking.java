package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class OrderTracking {

    @SerializedName("shippingRemark")
    private String shippingRemark;

    @SerializedName("orderId")
    private String orderId;

    @SerializedName("acceptRemark")
    private String acceptRemark;

    @SerializedName("vendorId")
    private String vendorId;

    @SerializedName("cancelledBy")
    private String cancelledBy;

    @SerializedName("acceptedDate")
    private String acceptedDate;

    @SerializedName("dispatchId")
    private String dispatchId;

    @SerializedName("deliveredDate")
    private String deliveredDate;

    @SerializedName("isPacked")
    private String isPacked;

    @SerializedName("fk_ProductDetailId")
    private String fkProductDetailId;

    @SerializedName("isShipped")
    private String isShipped;

    @SerializedName("cancelRemark")
    private String cancelRemark;

    @SerializedName("cancelledDate")
    private String cancelledDate;

    @SerializedName("packedDate")
    private String packedDate;

    @SerializedName("dispatchNo")
    private String dispatchNo;

    @SerializedName("deliveryRemark")
    private String deliveryRemark;

    @SerializedName("isCancelled")
    private String isCancelled;

    @SerializedName("packingRemark")
    private String packingRemark;

    @SerializedName("orderItemId")
    private String orderItemId;

    @SerializedName("shippedByAgency")
    private String shippedByAgency;

    @SerializedName("isDelivered")
    private String isDelivered;

    @SerializedName("fk_CancelId")
    private String fkCancelId;

    @SerializedName("isAccepted")
    private String isAccepted;

    @SerializedName("firmName")
    private String firmName;

    @SerializedName("shippedDate")
    private String shippedDate;

    public String getShippingRemark() {
        return shippingRemark;
    }

    public void setShippingRemark(String shippingRemark) {
        this.shippingRemark = shippingRemark;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAcceptRemark() {
        return acceptRemark;
    }

    public void setAcceptRemark(String acceptRemark) {
        this.acceptRemark = acceptRemark;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public String getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(String acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public String getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(String dispatchId) {
        this.dispatchId = dispatchId;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getIsPacked() {
        return isPacked;
    }

    public void setIsPacked(String isPacked) {
        this.isPacked = isPacked;
    }

    public String getFkProductDetailId() {
        return fkProductDetailId;
    }

    public void setFkProductDetailId(String fkProductDetailId) {
        this.fkProductDetailId = fkProductDetailId;
    }

    public String getIsShipped() {
        return isShipped;
    }

    public void setIsShipped(String isShipped) {
        this.isShipped = isShipped;
    }

    public String getCancelRemark() {
        return cancelRemark;
    }

    public void setCancelRemark(String cancelRemark) {
        this.cancelRemark = cancelRemark;
    }

    public String getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(String cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public String getPackedDate() {
        return packedDate;
    }

    public void setPackedDate(String packedDate) {
        this.packedDate = packedDate;
    }

    public String getDispatchNo() {
        return dispatchNo;
    }

    public void setDispatchNo(String dispatchNo) {
        this.dispatchNo = dispatchNo;
    }

    public String getDeliveryRemark() {
        return deliveryRemark;
    }

    public void setDeliveryRemark(String deliveryRemark) {
        this.deliveryRemark = deliveryRemark;
    }

    public String getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(String isCancelled) {
        this.isCancelled = isCancelled;
    }

    public String getPackingRemark() {
        return packingRemark;
    }

    public void setPackingRemark(String packingRemark) {
        this.packingRemark = packingRemark;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getShippedByAgency() {
        return shippedByAgency;
    }

    public void setShippedByAgency(String shippedByAgency) {
        this.shippedByAgency = shippedByAgency;
    }

    public String getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(String isDelivered) {
        this.isDelivered = isDelivered;
    }

    public String getFkCancelId() {
        return fkCancelId;
    }

    public void setFkCancelId(String fkCancelId) {
        this.fkCancelId = fkCancelId;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(String shippedDate) {
        this.shippedDate = shippedDate;
    }

    @Override
    public String toString() {
        return
                "OrderTracking{" +
                        "shippingRemark = '" + shippingRemark + '\'' +
                        ",orderId = '" + orderId + '\'' +
                        ",acceptRemark = '" + acceptRemark + '\'' +
                        ",vendorId = '" + vendorId + '\'' +
                        ",cancelledBy = '" + cancelledBy + '\'' +
                        ",acceptedDate = '" + acceptedDate + '\'' +
                        ",dispatchId = '" + dispatchId + '\'' +
                        ",deliveredDate = '" + deliveredDate + '\'' +
                        ",isPacked = '" + isPacked + '\'' +
                        ",fk_ProductDetailId = '" + fkProductDetailId + '\'' +
                        ",isShipped = '" + isShipped + '\'' +
                        ",cancelRemark = '" + cancelRemark + '\'' +
                        ",cancelledDate = '" + cancelledDate + '\'' +
                        ",packedDate = '" + packedDate + '\'' +
                        ",dispatchNo = '" + dispatchNo + '\'' +
                        ",deliveryRemark = '" + deliveryRemark + '\'' +
                        ",isCancelled = '" + isCancelled + '\'' +
                        ",packingRemark = '" + packingRemark + '\'' +
                        ",orderItemId = '" + orderItemId + '\'' +
                        ",shippedByAgency = '" + shippedByAgency + '\'' +
                        ",isDelivered = '" + isDelivered + '\'' +
                        ",fk_CancelId = '" + fkCancelId + '\'' +
                        ",isAccepted = '" + isAccepted + '\'' +
                        ",firmName = '" + firmName + '\'' +
                        ",shippedDate = '" + shippedDate + '\'' +
                        "}";
    }
}