package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataItem {

    @SerializedName("section_title")
    private String sectionTitle;

    @SerializedName("productImage")
    private List<ProductImageItem> productImage;

    @SerializedName("productSize")
    private List<ProductSizeItem> productSize;

    @SerializedName("products")
    private Products products;

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public List<ProductImageItem> getProductImage() {
        return productImage;
    }

    public void setProductImage(List<ProductImageItem> productImage) {
        this.productImage = productImage;
    }

    public List<ProductSizeItem> getProductSize() {
        return productSize;
    }

    public void setProductSize(List<ProductSizeItem> productSize) {
        this.productSize = productSize;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return
                "DataItem{" +
                        "section_title = '" + sectionTitle + '\'' +
                        ",productImage = '" + productImage + '\'' +
                        ",productSize = '" + productSize + '\'' +
                        ",products = '" + products + '\'' +
                        "}";
    }
}