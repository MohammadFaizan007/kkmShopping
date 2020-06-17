package kkm.com.core.model.request.profilemlm;

import com.google.gson.annotations.SerializedName;

public class RequestPersonalUpdate {

    @SerializedName("Fk_MemId")
    private String fkMemId;

    @SerializedName("Email")
    private String email;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("Address2")
    private String address2;

    @SerializedName("Tehsil")
    private String tehsil;

    @SerializedName("MarritalStatus")
    private String marritalStatus;

    @SerializedName("Address3")
    private String address3;

    @SerializedName("RelationwithNominee")
    private String relationwithNominee;

    @SerializedName("Gender")
    private String gender;

    @SerializedName("Address1")
    private String address1;

    @SerializedName("City")
    private String city;

    @SerializedName("Mobile")
    private String mobile;

    @SerializedName("PinCode")
    private String pinCode;

    @SerializedName("FathersName")
    private String fathersName;

    @SerializedName("DOB")
    private String dOB;

    @SerializedName("State")
    private String state;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("NomineeName")
    private String nomineeName;

    @SerializedName("PanCard")
    private String panCard;

    @SerializedName("AadharNo")
    private String aadharNo;

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public void setFkMemId(String fkMemId) {
        this.fkMemId = fkMemId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public void setMarritalStatus(String marritalStatus) {
        this.marritalStatus = marritalStatus;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public void setRelationwithNominee(String relationwithNominee) {
        this.relationwithNominee = relationwithNominee;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }
}