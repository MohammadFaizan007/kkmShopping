package kkm.com.core.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OrderListItem implements Parcelable {

    public static final Parcelable.Creator<OrderListItem> CREATOR
            = new Parcelable.Creator<OrderListItem>() {
        public OrderListItem createFromParcel(Parcel in) {
            return new OrderListItem(in);
        }

        public OrderListItem[] newArray(int size) {
            return new OrderListItem[size];
        }
    };
    @SerializedName("pincode")
    private String pincode;
    @SerializedName("orderNumber")
    private String orderNumber;
    @SerializedName("address")
    private String address;
    @SerializedName("totalPrice")
    private String totalPrice;
    @SerializedName("orderId")
    private String orderId;
    @SerializedName("city")
    private String city;
    @SerializedName("orderItemId")
    private String orderItemId;
    @SerializedName("addressType")
    private String addressType;
    @SerializedName("vendorId")
    private String vendorId;
    @SerializedName("description")
    private String description;
    @SerializedName("mobileNo")
    private String mobileNo;
    @SerializedName("oredrStatus")
    private String oredrStatus;
    @SerializedName("isRefundable")
    private String isRefundable;
    @SerializedName("productQuantity")
    private String productQuantity;
    @SerializedName("productImage")
    private String productImage;
    @SerializedName("shippingPrice")
    private String shippingPrice;
    @SerializedName("price")
    private String price;
    @SerializedName("fk_ProductDetailId")
    private String fkProductDetailId;
    @SerializedName("firmName")
    private String firmName;
    @SerializedName("isExchangeable")
    private String isExchangeable;
    @SerializedName("state")
    private String state;
    @SerializedName("landmark")
    private String landmark;
    @SerializedName("paroductName")
    private String paroductName;
    @SerializedName("orderDate")
    private String orderDate;

    public OrderListItem(Parcel in) {
        pincode = in.readString();
        orderNumber = in.readString();
        address = in.readString();
        totalPrice = in.readString();
        orderId = in.readString();
        city = in.readString();
        orderItemId = in.readString();
        addressType = in.readString();
        vendorId = in.readString();
        description = in.readString();
        mobileNo = in.readString();
        oredrStatus = in.readString();
        isRefundable = in.readString();
        productQuantity = in.readString();
        productImage = in.readString();
        shippingPrice = in.readString();
        price = in.readString();
        fkProductDetailId = in.readString();
        firmName = in.readString();
        isExchangeable = in.readString();
        state = in.readString();
        landmark = in.readString();
        paroductName = in.readString();
        orderDate = in.readString();
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOredrStatus() {
        return oredrStatus;
    }

    public void setOredrStatus(String oredrStatus) {
        this.oredrStatus = oredrStatus;
    }

    public String getIsRefundable() {
        return isRefundable;
    }

    public void setIsRefundable(String isRefundable) {
        this.isRefundable = isRefundable;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFkProductDetailId() {
        return fkProductDetailId;
    }

    public void setFkProductDetailId(String fkProductDetailId) {
        this.fkProductDetailId = fkProductDetailId;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getIsExchangeable() {
        return isExchangeable;
    }

    public void setIsExchangeable(String isExchangeable) {
        this.isExchangeable = isExchangeable;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getParoductName() {
        return paroductName;
    }

    public void setParoductName(String paroductName) {
        this.paroductName = paroductName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return
                "OrderListItem{" +
                        "pincode = '" + pincode + '\'' +
                        ",orderNumber = '" + orderNumber + '\'' +
                        ",address = '" + address + '\'' +
                        ",totalPrice = '" + totalPrice + '\'' +
                        ",orderId = '" + orderId + '\'' +
                        ",city = '" + city + '\'' +
                        ",orderItemId = '" + orderItemId + '\'' +
                        ",addressType = '" + addressType + '\'' +
                        ",vendorId = '" + vendorId + '\'' +
                        ",description = '" + description + '\'' +
                        ",mobileNo = '" + mobileNo + '\'' +
                        ",oredrStatus = '" + oredrStatus + '\'' +
                        ",isRefundable = '" + isRefundable + '\'' +
                        ",productQuantity = '" + productQuantity + '\'' +
                        ",productImage = '" + productImage + '\'' +
                        ",shippingPrice = '" + shippingPrice + '\'' +
                        ",price = '" + price + '\'' +
                        ",fk_ProductDetailId = '" + fkProductDetailId + '\'' +
                        ",firmName = '" + firmName + '\'' +
                        ",isExchangeable = '" + isExchangeable + '\'' +
                        ",state = '" + state + '\'' +
                        ",landmark = '" + landmark + '\'' +
                        ",paroductName = '" + paroductName + '\'' +
                        ",orderDate = '" + orderDate + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pincode);
        dest.writeString(this.orderNumber);
        dest.writeString(this.address);
        dest.writeString(this.totalPrice);
        dest.writeString(this.orderId);
        dest.writeString(this.city);
        dest.writeString(this.orderItemId);
        dest.writeString(this.addressType);
        dest.writeString(this.vendorId);
        dest.writeString(this.description);
        dest.writeString(this.mobileNo);
        dest.writeString(this.oredrStatus);
        dest.writeString(this.isRefundable);
        dest.writeString(this.productQuantity);
        dest.writeString(this.productImage);
        dest.writeString(this.shippingPrice);
        dest.writeString(this.price);
        dest.writeString(this.fkProductDetailId);
        dest.writeString(this.firmName);
        dest.writeString(this.isExchangeable);
        dest.writeString(this.state);
        dest.writeString(this.landmark);
        dest.writeString(this.paroductName);
        dest.writeString(this.orderDate);
    }


}