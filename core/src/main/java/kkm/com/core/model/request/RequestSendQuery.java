package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestSendQuery {

    @SerializedName("Fk_MemId")
    private String fkMemId;

    @SerializedName("Message")
    private String message;

    @SerializedName("Subject")
    private String subject;

    @SerializedName("IsAttachment")
    private String isAttachment ;

    public String getFkMemId() {
        return fkMemId;
    }

    public void setFkMemId(String fkMemId) {
        this.fkMemId = fkMemId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getIsAttachment() {
        return isAttachment;
    }

    public void setIsAttachment(String isAttachment) {
        this.isAttachment = isAttachment;
    }
}