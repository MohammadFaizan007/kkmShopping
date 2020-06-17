package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFundLog {

    @SerializedName("response")
    private String response;

    @SerializedName("fundTransferLog")
    private List<FundTransferLogItem> fundTransferLog;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<FundTransferLogItem> getFundTransferLog() {
        return fundTransferLog;
    }

    public void setFundTransferLog(List<FundTransferLogItem> fundTransferLog) {
        this.fundTransferLog = fundTransferLog;
    }
}