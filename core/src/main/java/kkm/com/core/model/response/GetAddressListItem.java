package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class GetAddressListItem {

    @SerializedName("fK_CustID")
    private String fKCustID;

    @SerializedName("address")
    private String address;

    @SerializedName("state")
    private String state;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("landmark")
    private String landmark;

    @SerializedName("addressMode")
    private String addressMode;

    @SerializedName("ispermanent")
    private String ispermanent;

    @SerializedName("city")
    private String city;

    @SerializedName("addressType")
    private String addressType;

    @SerializedName("ship_AddressId")
    private String shipAddressId;

    @SerializedName("pincode")
    private String pincode;

    public String getFKCustID() {
        return fKCustID;
    }

    public void setFKCustID(String fKCustID) {
        this.fKCustID = fKCustID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddressMode() {
        return addressMode;
    }

    public void setAddressMode(String addressMode) {
        this.addressMode = addressMode;
    }

    public String getIspermanent() {
        return ispermanent;
    }

    public void setIspermanent(String ispermanent) {
        this.ispermanent = ispermanent;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getShipAddressId() {
        return shipAddressId;
    }

    public void setShipAddressId(String shipAddressId) {
        this.shipAddressId = shipAddressId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return
                "GetAddressListItem{" +
                        "fK_CustID = '" + fKCustID + '\'' +
                        ",address = '" + address + '\'' +
                        ",state = '" + state + '\'' +
                        ",fullName = '" + fullName + '\'' +
                        ",landmark = '" + landmark + '\'' +
                        ",addressMode = '" + addressMode + '\'' +
                        ",ispermanent = '" + ispermanent + '\'' +
                        ",city = '" + city + '\'' +
                        ",addressType = '" + addressType + '\'' +
                        ",ship_AddressId = '" + shipAddressId + '\'' +
                        ",pincode = '" + pincode + '\'' +
                        "}";
    }
}