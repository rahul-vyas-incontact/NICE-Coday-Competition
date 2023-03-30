package com.nice.avishkar.utils;

import com.nice.avishkar.PredictedProductInfo;
import com.nice.avishkar.PredictedWarehouseInfo;
import com.nice.avishkar.Product;
import com.nice.avishkar.SellDayHistory;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CalculationUtil {


    public static final BigDecimal SALE_RATE = BigDecimal.valueOf(10);
    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    public PredictedWarehouseInfo calculatePrediction(Map<String, Product> productMap) {
        PredictedWarehouseInfo predictedWarehouseInfo = new PredictedWarehouseInfo();
        List<PredictedProductInfo> predictedProductInfos = new ArrayList<>();
        for (Product product : productMap.values()) {
            PredictedProductInfo predictedProductInfo = new PredictedProductInfo();
            predictedProductInfo.setProductId(Long.valueOf(product.getProductId()));
            predictedProductInfo.setProductName(product.getProductName());
            if (product.getQuantity() > 0) {
                double diff = calculatePredictionPerROI(product).doubleValue() + calculatePredictionPerASR(product).doubleValue();
                predictedProductInfo.setPredictedQuantity(Double.valueOf(Math.ceil(diff / 2)).longValue());
            } else {
                predictedProductInfo.setPredictedQuantity(0l);
            }
            predictedProductInfos.add(predictedProductInfo);
        }
        predictedProductInfos.sort(PredictedProductInfo::compareByQuantity);
        predictedWarehouseInfo.setProductList(predictedProductInfos);
        predictedWarehouseInfo.setWarehouseCapacity(predictedProductInfos.stream().mapToLong(PredictedProductInfo::getPredictedQuantity).sum());
        return predictedWarehouseInfo;
    }

    public BigDecimal calculatePredictionPerROI(Product product) {
        calculateROI(product);
        double availableQuantity = product.getQuantity();
        return BigDecimal.valueOf(Math.ceil(((product.getRoi().doubleValue() / 10) * (availableQuantity)) / 100) + availableQuantity);
    }

    public BigDecimal calculatePredictionPerASR(Product product) {
        calculateASR(product);
        double availableQuantity = product.getQuantity();
        return BigDecimal.valueOf(Math.ceil(product.getAsr().doubleValue() * availableQuantity / 100) + availableQuantity);

    }


    public void calculateROI(Product product) {
        BigDecimal roi = BigDecimal.valueOf(Math.ceil((getTotalProfit(product) / (getTotalInvestment(product)).doubleValue()) * 100));
        product.setRoi(roi);
    }

    public void calculateASR(Product product) {
        double sellRateDay1 = Math.ceil(getSaleRatePerDay(product, product.getSellDayHistoryDay1()).multiply(BigDecimal.valueOf(0.1)).doubleValue());
        double sellRateDay2 = Math.ceil(getSaleRatePerDay(product, product.getSellDayHistoryDay2()).multiply(BigDecimal.valueOf(0.2)).doubleValue());
        double sellRateDay3 = Math.ceil(getSaleRatePerDay(product, product.getSellDayHistoryDay3()).multiply(BigDecimal.valueOf(0.3)).doubleValue());
        double sellRateDay4 = Math.ceil(getSaleRatePerDay(product, product.getSellDayHistoryDay4()).multiply(BigDecimal.valueOf(0.4)).doubleValue());
        List<Double> sellRates = Arrays.asList(sellRateDay1, sellRateDay2, sellRateDay3, sellRateDay4);
        double sumOfRates = sellRateDay1 + sellRateDay2 + sellRateDay3 + sellRateDay4;
        product.setAsr(BigDecimal.valueOf(Math.ceil(sumOfRates / sellRates.size())));

    }

    private double getQuantityPerDay(List<SellDayHistory> sellDayHistories) {
        if (!CollectionUtils.isEmpty(sellDayHistories)) {
            return sellDayHistories.stream().mapToDouble(SellDayHistory::getSellQuantity).sum();
        }
        return 0.0;
    }

    private BigDecimal getSaleRatePerDay(Product product, List<SellDayHistory> sellDayHistories) {
        double saleRate = getQuantityPerDay(sellDayHistories) / product.getQuantity();
        return BigDecimal.valueOf(saleRate).multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.CEILING);
    }

    private double getTotalProfit(Product product) {
        double sumOfQuantityAllDays = getSumOfQuantityAllDays(product);
        return Math.ceil((sumOfQuantityAllDays * (product.getProfitMargin().doubleValue() / 100)) * product.getBuyPrice());
        //return (BigDecimal.valueOf(sumOfQuantityAllDays).multiply(product.getProfitMargin().divide(HUNDRED, RoundingMode.CEILING))).multiply(BigDecimal.valueOf(product.getBuyPrice()));
    }

    private double getSumOfQuantityAllDays(Product product) {
        double sumOfQuantityDay1 = getQuantityPerDay(product.getSellDayHistoryDay1());
        double sumOfQuantityDay2 = sumOfQuantityDay1 + getQuantityPerDay(product.getSellDayHistoryDay2());
        double sumOfQuantityDay3 = sumOfQuantityDay2 + getQuantityPerDay(product.getSellDayHistoryDay3());
        double sumOfQuantityAllDays = sumOfQuantityDay3 + getQuantityPerDay(product.getSellDayHistoryDay4());
        return sumOfQuantityAllDays;
    }

    private BigDecimal getTotalInvestment(Product product) {
        //double availableQuantity = getAvailableQuantity(product);
        return BigDecimal.valueOf(product.getQuantity()).multiply(BigDecimal.valueOf(product.getBuyPrice()));
    }

  /*  private double getAvailableQuantity(Product product) {
        double availableQuantity = getSumOfQuantityAllDays(product) - product.getQuantity();
        return availableQuantity;
    }*/

}
