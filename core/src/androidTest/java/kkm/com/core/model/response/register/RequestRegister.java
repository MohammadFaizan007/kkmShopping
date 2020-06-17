package kkm.com.core.model.response.register;

import com.google.gson.annotations.SerializedName;

public class RequestRegister {

    @SerializedName("AlternateMobile")
    private String alternateMobile;

    @SerializedName("CreatedBy")
    private String createdBy;

    @SerializedName("Email")
    private String email;

    @SerializedName("Tahsil")
    private String tahsil;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("Gender")
    private String gender;

    @SerializedName("City")
    private String city;

    @SerializedName("Mobile")
    private String mobile;

    @SerializedName("PinCode")
    private String pinCode;

    @SerializedName("AddressLine1")
    private String addressLine1;

    @SerializedName("SponsorCode")
    private String sponsorCode;

    @SerializedName("DOB")
    private String dOB;

    @SerializedName("State")
    private String state;

    @SerializedName("PANNo")
    private String pANNo;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("FatherName")
    private String fatherName;

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTahsil() {
        return tahsil;
    }

    public void setTahsil(String tahsil) {
        this.tahsil = tahsil;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getSponsorCode() {
        return sponsorCode;
    }

    public void setSponsorCode(String sponsorCode) {
        this.sponsorCode = sponsorCode;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPANNo() {
        return pANNo;
    }

    public void setPANNo(String pANNo) {
        this.pANNo = pANNo;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }
}