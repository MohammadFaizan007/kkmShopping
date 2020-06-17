package kkm.com.core.model.response.register;

import com.google.gson.annotations.SerializedName;

public class ResponseRegister {

    @SerializedName("memberRegistrationDetails")
    private MemberRegistrationDetails memberRegistrationDetails;

    @SerializedName("response")
    private String response;

    @SerializedName("message")
    private String message;

    public MemberRegistrationDetails getMemberRegistrationDetails() {
        return memberRegistrationDetails;
    }

    public void setMemberRegistrationDetails(MemberRegistrationDetails memberRegistrationDetails) {
        this.memberRegistrationDetails = memberRegistrationDetails;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}