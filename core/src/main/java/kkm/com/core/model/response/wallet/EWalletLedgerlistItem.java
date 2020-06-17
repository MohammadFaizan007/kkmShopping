package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

public class EWalletLedgerlistItem {

    @SerializedName("balance")
    private String balance;

    @SerializedName("narration")
    private String narration;

    @SerializedName("transDate")
    private String transDate;

    @SerializedName("drAmount")
    private String drAmount;

    @SerializedName("crAmount")
    private String crAmount;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getDrAmount() {
        return drAmount;
    }

    public void setDrAmount(String drAmount) {
        this.drAmount = drAmount;
    }

    public String getCrAmount() {
        return crAmount;
    }

    public void setCrAmount(String crAmount) {
        this.crAmount = crAmount;
    }
}