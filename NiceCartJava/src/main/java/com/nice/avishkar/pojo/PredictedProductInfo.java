package com.nice.avishkar.pojo;

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


    public static int compareByQuantity(PredictedProductInfo o1, PredictedProductInfo o2) {
        if (o1.getPredictedQuantity() > o2.getPredictedQuantity()) {
            return -1;
        } else if (o1.getPredictedQuantity() == o2.getPredictedQuantity()) {
            return o1.getProductName().compareTo(o2.getProductName());
        } else {
            return 1;
        }
    }
}
