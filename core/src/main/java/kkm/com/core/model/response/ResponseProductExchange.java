package kkm.com.core.model.response;

public class ResponseProductExchange {
    private String response;
    private String replacementId;
    private String message;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getReplacementId() {
        return replacementId;
    }

    public void setReplacementId(String replacementId) {
        this.replacementId = replacementId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return
                "ResponseProductExchange{" +
                        "response = '" + response + '\'' +
                        ",replacementId = '" + replacementId + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
