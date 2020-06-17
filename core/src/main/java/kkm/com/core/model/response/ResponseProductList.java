package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseProductList {

    @SerializedName("productList")
    private List<ProductListItem> productList;

    @SerializedName("response")
    private String response;

    public List<ProductListItem> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListItem> productList) {
        this.productList = productList;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}