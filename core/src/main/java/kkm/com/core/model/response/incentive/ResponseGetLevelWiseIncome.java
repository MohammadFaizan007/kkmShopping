package kkm.com.core.model.response.incentive;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetLevelWiseIncome {

//    "paidIncome": "0.0000",
//            "unclearBalance": "355.3365"

    @SerializedName("response")
    private String response;

    @SerializedName("grossIncome")
    private String grossIncome;

    @SerializedName("tds")
    private String tds;

    @SerializedName("paidIncome")
    private String paidIncome;

    @SerializedName("unclearBalance")
    private String unclearBalance;

    public String getPaidIncome() {
        return paidIncome;
    }

    public String getUnclearBalance() {
        return unclearBalance;
    }

    @SerializedName("netIncome")
    private String netIncome;


    @SerializedName("listLevelWiseIncome")
    private List<ListLevelWiseIncomeItem> listLevelWiseIncome;

    public String getResponse() {
        return response;
    }

    public List<ListLevelWiseIncomeItem> getListLevelWiseIncome() {
        return listLevelWiseIncome;
    }

    public String getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(String grossIncome) {
        this.grossIncome = grossIncome;
    }

    public String getTds() {
        return tds;
    }

    public void setTds(String tds) {
        this.tds = tds;
    }

    public String getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(String netIncome) {
        this.netIncome = netIncome;
    }
}