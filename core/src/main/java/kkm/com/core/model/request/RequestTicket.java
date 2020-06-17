package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestTicket {

    @SerializedName("Fk_MemId")
    private String fkMemId;

    public String getFkMemId() {
        return fkMemId;
    }

    public void setFkMemId(String fkMemId) {
        this.fkMemId = fkMemId;
    }

    @Override
    public String toString() {
        return
                "RequestTicket{" +
                        "fk_MemId = '" + fkMemId + '\'' +
                        "}";
    }
}