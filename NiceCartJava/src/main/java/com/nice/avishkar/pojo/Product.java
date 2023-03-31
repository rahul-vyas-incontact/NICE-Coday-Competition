package com.nice.avishkar.pojo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Product {

    private String productId;
    private String productName;
    private long buyPrice;
    private long sellPrice;
    private long quantity;
    private BigDecimal roi;
    private BigDecimal asr;
    private BigDecimal profitMargin;
    Map<Integer, List<SellDayHistory>> sellDayHistoryMap;

    public BigDecimal getRoi() {
        return roi;
    }

    public BigDecimal getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(BigDecimal profitMargin) {
        this.profitMargin = profitMargin;
    }

    public void setRoi(BigDecimal roi) {
        this.roi = roi;
    }

    public BigDecimal getAsr() {
        return asr;
    }

    public void setAsr(BigDecimal asr) {
        this.asr = asr;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(long buyPrice) {
        this.buyPrice = buyPrice;
    }

    public long getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Map<Integer, List<SellDayHistory>> getSellDayHistoryMap() {
        return sellDayHistoryMap;
    }

    public void setSellDayHistoryMap(Map<Integer, List<SellDayHistory>> sellDayHistoryMap) {
        this.sellDayHistoryMap = sellDayHistoryMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
