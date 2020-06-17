package kkm.com.core.model.response.updateProfile;

import com.google.gson.annotations.SerializedName;

public class PersonalDetailOld {

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("pincode")
    private String pincode;

    @SerializedName("address")
    private String address;

    @SerializedName("loginId")
    private String loginId;

    @SerializedName("city")
    private String city;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("state")
    private String state;

    @SerializedName("email")
    private String email;

    @SerializedName("imageUrl")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPincode() {
        return pincode;
    }

    public String getAddress() {
        return address;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getCity() {
        return city;
    }

    public String getMobile() {
        return mobile;
    }

    public String getState() {
        return state;
    }

    public String getEmail() {
        return email;
    }
}