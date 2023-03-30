package com.nice.avishkar;

import java.math.BigDecimal;
import java.util.List;
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
    private List<SellDayHistory> sellDayHistoryDay1;

    private List<SellDayHistory> sellDayHistoryDay2;
    private List<SellDayHistory> sellDayHistoryDay3;
    private List<SellDayHistory> sellDayHistoryDay4;


    public List<SellDayHistory> getSellDayHistoryDay1() {
        return sellDayHistoryDay1;
    }

    public void setSellDayHistoryDay1(List<SellDayHistory> sellDayHistoryDay1) {
        this.sellDayHistoryDay1 = sellDayHistoryDay1;
    }

    public List<SellDayHistory> getSellDayHistoryDay2() {
        return sellDayHistoryDay2;
    }

    public void setSellDayHistoryDay2(List<SellDayHistory> sellDayHistoryDay2) {
        this.sellDayHistoryDay2 = sellDayHistoryDay2;
    }

    public List<SellDayHistory> getSellDayHistoryDay3() {
        return sellDayHistoryDay3;
    }

    public void setSellDayHistoryDay3(List<SellDayHistory> sellDayHistoryDay3) {
        this.sellDayHistoryDay3 = sellDayHistoryDay3;
    }

    public List<SellDayHistory> getSellDayHistoryDay4() {
        return sellDayHistoryDay4;
    }

    public void setSellDayHistoryDay4(List<SellDayHistory> sellDayHistoryDay4) {
        this.sellDayHistoryDay4 = sellDayHistoryDay4;
    }

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
