package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseReferalName {

    @SerializedName("response")
    private String response;

    @SerializedName("name")
    private String name;

    @SerializedName("fK_MemId")
    private String fK_MemId;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getfK_MemId() {
        return fK_MemId;
    }

    public void setfK_MemId(String fK_MemId) {
        this.fK_MemId = fK_MemId;
    }
}