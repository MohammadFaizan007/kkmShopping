package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePincodeDetail {

    @SerializedName("Status")
    private String status;

    @SerializedName("Message")
    private String message;

    @SerializedName("PostOffice")
    private List<PostOfficeItem> postOffice;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PostOfficeItem> getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(List<PostOfficeItem> postOffice) {
        this.postOffice = postOffice;
    }
}