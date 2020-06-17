package kkm.com.core.model.response.bill;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllProviderlistItem {

    @SerializedName("gasBillPaymentProviderlist")
    private List<BillPaymentProviderListItem> gasBillPaymentProviderlist;

    @SerializedName("broadbandProviderProviderlist")
    private List<BillPaymentProviderListItem> broadbandProviderProviderlist;

    @SerializedName("postpaidrechargeProviderlist")
    private List<BillPaymentProviderListItem> postpaidrechargeProviderlist;

    @SerializedName("insuranceProviderlist")
    private List<BillPaymentProviderListItem> insuranceProviderlist;

    @SerializedName("waterbillpaymentProviderlist")
    private List<BillPaymentProviderListItem> waterbillpaymentProviderlist;

    @SerializedName("electricityBillPaymentProviderlist")
    private List<BillPaymentProviderListItem> electricityBillPaymentProviderlist;

    @SerializedName("dometsticMoneyTransferProviderlist")
    private List<BillPaymentProviderListItem> dometsticMoneyTransferProviderlist;

    public List<BillPaymentProviderListItem> getGasBillPaymentProviderlist() {
        return gasBillPaymentProviderlist;
    }

    public void setGasBillPaymentProviderlist(List<BillPaymentProviderListItem> gasBillPaymentProviderlist) {
        this.gasBillPaymentProviderlist = gasBillPaymentProviderlist;
    }

    public List<BillPaymentProviderListItem> getBroadbandProviderProviderlist() {
        return broadbandProviderProviderlist;
    }

    public void setBroadbandProviderProviderlist(List<BillPaymentProviderListItem> broadbandProviderProviderlist) {
        this.broadbandProviderProviderlist = broadbandProviderProviderlist;
    }

    public List<BillPaymentProviderListItem> getPostpaidrechargeProviderlist() {
        return postpaidrechargeProviderlist;
    }

    public void setPostpaidrechargeProviderlist(List<BillPaymentProviderListItem> postpaidrechargeProviderlist) {
        this.postpaidrechargeProviderlist = postpaidrechargeProviderlist;
    }

    public List<BillPaymentProviderListItem> getInsuranceProviderlist() {
        return insuranceProviderlist;
    }

    public void setInsuranceProviderlist(List<BillPaymentProviderListItem> insuranceProviderlist) {
        this.insuranceProviderlist = insuranceProviderlist;
    }

    public List<BillPaymentProviderListItem> getWaterbillpaymentProviderlist() {
        return waterbillpaymentProviderlist;
    }

    public void setWaterbillpaymentProviderlist(List<BillPaymentProviderListItem> waterbillpaymentProviderlist) {
        this.waterbillpaymentProviderlist = waterbillpaymentProviderlist;
    }

    public List<BillPaymentProviderListItem> getElectricityBillPaymentProviderlist() {
        return electricityBillPaymentProviderlist;
    }

    public void setElectricityBillPaymentProviderlist(List<BillPaymentProviderListItem> electricityBillPaymentProviderlist) {
        this.electricityBillPaymentProviderlist = electricityBillPaymentProviderlist;
    }

    public List<BillPaymentProviderListItem> getDometsticMoneyTransferProviderlist() {
        return dometsticMoneyTransferProviderlist;
    }

    public void setDometsticMoneyTransferProviderlist(List<BillPaymentProviderListItem> dometsticMoneyTransferProviderlist) {
        this.dometsticMoneyTransferProviderlist = dometsticMoneyTransferProviderlist;
    }
}