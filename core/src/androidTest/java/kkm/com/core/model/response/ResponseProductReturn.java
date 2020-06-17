package kkm.com.core.model.response;

public class ResponseProductReturn {
    private String response;
    private String returnId;
    private String message;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
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
                "ResponseProductReturn{" +
                        "response = '" + response + '\'' +
                        ",returnId = '" + returnId + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
