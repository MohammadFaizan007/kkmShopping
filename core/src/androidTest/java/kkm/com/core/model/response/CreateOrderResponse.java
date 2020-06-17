package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class CreateOrderResponse {

    @SerializedName("response")
    private String response;

    @SerializedName("orderNo")
    private String orderNo;

    @SerializedName("uType")
    private String uType;

    @SerializedName("walletAmmount")
    private String walletAmmount;

    @SerializedName("orderId")
    private Object orderId;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUType() {
        return uType;
    }

    public void setUType(String uType) {
        this.uType = uType;
    }

    public String getWalletAmmount() {
        return walletAmmount;
    }

    public void setWalletAmmount(String walletAmmount) {
        this.walletAmmount = walletAmmount;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return
                "CreateOrderResponse{" +
                        "response = '" + response + '\'' +
                        ",orderNo = '" + orderNo + '\'' +
                        ",uType = '" + uType + '\'' +
                        ",walletAmmount = '" + walletAmmount + '\'' +
                        ",orderId = '" + orderId + '\'' +
                        "}";
    }
}