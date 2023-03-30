package com.nice.avishkar;

import com.nice.avishkar.utils.CSVDataParser;
import com.nice.avishkar.utils.CalculationUtil;
import com.nice.avishkar.utils.DataParser;

import java.io.IOException;
import java.util.Map;

public class InventoryPredictorImpl implements InventoryPredictor {
    private DataParser csvDataParser;
    private CalculationUtil calculationUtil;

    public InventoryPredictorImpl() {
        csvDataParser = new CSVDataParser();
        calculationUtil = new CalculationUtil();
    }

    @Override
    public PredictedWarehouseInfo predictWarehouseCapacityWithProductDetails(ResourceInfo resourceInfo) throws IOException {
        //Code here
        //i. Read the values from all files and map to per product
        Map<String, Product> productSellData = csvDataParser.parse(resourceInfo);
        return calculationUtil.calculatePrediction(productSellData);
    }
}
