package com.nice.avishkar.impl.utils;

import com.nice.avishkar.pojo.PredictedProductInfo;
import com.nice.avishkar.pojo.PredictedWarehouseInfo;
import com.nice.avishkar.pojo.Product;
import com.nice.avishkar.pojo.SellDayHistory;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculationUtil {

    public PredictedWarehouseInfo calculatePredictedWareHouseInfo(Map<String, Product> productMap) {
        PredictedWarehouseInfo predictedWarehouseInfo = new PredictedWarehouseInfo();
        List<PredictedProductInfo> predictedProductInfos = new ArrayList<>();
        for (Product product : productMap.values()) {
            calculatedPredictedProductInfo(predictedProductInfos, product);
        }
        predictedProductInfos.sort(PredictedProductInfo::compareByQuantity);
        predictedWarehouseInfo.setProductList(predictedProductInfos);
        predictedWarehouseInfo.setWarehouseCapacity(predictedProductInfos.stream().mapToLong(PredictedProductInfo::getPredictedQuantity).sum());
        return predictedWarehouseInfo;
    }

    private void calculatedPredictedProductInfo(List<PredictedProductInfo> predictedProductInfos, Product product) {
        PredictedProductInfo predictedProductInfo = new PredictedProductInfo();
        predictedProductInfo.setProductId(Long.parseLong(product.getProductId()));
        predictedProductInfo.setProductName(product.getProductName());
        if (product.getQuantity() > 0) {
            double predictedQuantity = calculatePredictionPerROI(product).doubleValue() + calculatePredictionPerASR(product).doubleValue();
            predictedProductInfo.setPredictedQuantity(Double.valueOf(Math.ceil(predictedQuantity / 2)).longValue());
        } else {
            predictedProductInfo.setPredictedQuantity(0l);
        }
        predictedProductInfos.add(predictedProductInfo);
    }

    private BigDecimal calculatePredictionPerROI(Product product) {
        calculateROI(product);
        double availableQuantity = product.getQuantity();
        return BigDecimal.valueOf(Math.ceil(((product.getRoi().doubleValue() / 10) * (availableQuantity)) / Constants.PERCENTAGE_FACTOR) + availableQuantity);
    }

    private BigDecimal calculatePredictionPerASR(Product product) {
        calculateASR(product);
        double availableQuantity = product.getQuantity();
        return BigDecimal.valueOf(Math.ceil(product.getAsr().doubleValue() * availableQuantity / Constants.PERCENTAGE_FACTOR) + availableQuantity);
    }

    private void calculateROI(Product product) {
        BigDecimal roi = BigDecimal.valueOf(Math.ceil((getTotalProfit(product) / (getTotalInvestment(product)).doubleValue()) * Constants.PERCENTAGE_FACTOR));
        product.setRoi(roi);
    }

    private void calculateASR(Product product) {
        double sum = product.getSellDayHistoryMap().entrySet().stream().mapToDouble(day -> {
            double weightingFactor = getWeightingFactorPerDay(day);
            return Math.ceil(getSaleRatePerDay(product, day.getValue()) * weightingFactor);
        }).sum();
        product.setAsr(BigDecimal.valueOf(Math.ceil(sum / Constants.HISTORY_PERIOD_IN_DAY)));
    }

    private static double getWeightingFactorPerDay(Map.Entry<Integer, List<SellDayHistory>> day) {
        return BigDecimal.valueOf(Constants.WEIGHTING_FACTOR * day.getKey()).setScale(1, RoundingMode.DOWN).doubleValue();
    }

    private double getQuantityPerDay(List<SellDayHistory> sellDayHistories) {
        if (!CollectionUtils.isEmpty(sellDayHistories)) {
            return sellDayHistories.stream().mapToDouble(SellDayHistory::getSellQuantity).sum();
        }
        return 0.0;
    }

    private double getSaleRatePerDay(Product product, List<SellDayHistory> sellDayHistories) {
        double saleRate = getQuantityPerDay(sellDayHistories) / product.getQuantity();
        return Math.ceil(saleRate * Constants.PERCENTAGE_FACTOR);
    }

    private double getTotalProfit(Product product) {
        double sumOfQuantityAllDays = getSumOfQuantityAllDays(product);
        return Math.ceil((sumOfQuantityAllDays * (product.getProfitMargin().doubleValue() / Constants.PERCENTAGE_FACTOR)) * product.getBuyPrice());
    }

    private double getSumOfQuantityAllDays(Product product) {
        return product.getSellDayHistoryMap().entrySet().stream().
                mapToDouble(day -> getQuantityPerDay(day.getValue())).sum();
    }

    private BigDecimal getTotalInvestment(Product product) {
        return BigDecimal.valueOf(product.getQuantity()).multiply(BigDecimal.valueOf(product.getBuyPrice()));
    }
}
