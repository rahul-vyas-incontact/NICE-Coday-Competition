package com.nice.avishkar;

public class PredictedProductInfo {

    long productId;
    String productName;
    long predictedQuantity;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getPredictedQuantity() {
        return predictedQuantity;
    }

    public void setPredictedQuantity(long predictedQuantity) {
        this.predictedQuantity = predictedQuantity;
    }

    @Override
    public String toString() {
        return "PredictedProductInfo{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", predictedQuantity=" + predictedQuantity +
                '}';
    }
}
