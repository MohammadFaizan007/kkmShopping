package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseOrderList {

    @SerializedName("response")
    private String response;

    @SerializedName("orderList")
    private List<OrderListItem> orderList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<OrderListItem> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListItem> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return
                "ResponseOrderList{" +
                        "response = '" + response + '\'' +
                        ",orderList = '" + orderList + '\'' +
                        "}";
    }
}