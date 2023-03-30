package com.nice.avishkar;

import java.util.List;

public class PredictedWarehouseInfo {
    long warehouseCapacity;
    List<PredictedProductInfo> productList;

    public long getWarehouseCapacity() {
        return warehouseCapacity;
    }

    public void setWarehouseCapacity(long warehouseCapacity) {
        this.warehouseCapacity = warehouseCapacity;
    }

    public List<PredictedProductInfo> getProductList() {
        return productList;
    }

    public void setProductList(List<PredictedProductInfo> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "PredictedWarehouseInfo{" +
                "warehouseCapacity=" + warehouseCapacity +
                ", productList=" + productList +
                '}';
    }
}