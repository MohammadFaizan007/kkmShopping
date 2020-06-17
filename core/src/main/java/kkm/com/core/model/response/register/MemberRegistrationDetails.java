package kkm.com.core.model.response.register;

import com.google.gson.annotations.SerializedName;

public class MemberRegistrationDetails {

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("password")
    private String password;

    @SerializedName("loginId")
    private String loginId;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("tPassword")
    private String tPassword;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTPassword() {
        return tPassword;
    }

    public void setTPassword(String tPassword) {
        this.tPassword = tPassword;
    }
}