package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestCreateLoginPIN {

    @SerializedName("GeneratePin")
    private String generatePin;

    @SerializedName("FK_MemId")
    private String fKMemId;

    public void setGeneratePin(String generatePin) {
        this.generatePin = generatePin;
    }

    public void setFKMemId(String fKMemId) {
        this.fKMemId = fKMemId;
    }
}