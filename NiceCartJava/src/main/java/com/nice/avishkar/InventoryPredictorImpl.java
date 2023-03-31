package com.nice.avishkar;

import com.nice.avishkar.dao.CSVDataParser;
import com.nice.avishkar.impl.utils.CalculationUtil;
import com.nice.avishkar.dao.DataParser;
import com.nice.avishkar.impl.utils.InventoryPredictor;
import com.nice.avishkar.pojo.PredictedWarehouseInfo;
import com.nice.avishkar.pojo.Product;
import com.nice.avishkar.pojo.ResourceInfo;

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
        Map<String, Product> productSellData = csvDataParser.getProductSellData(resourceInfo);
        return calculationUtil.calculatePredictedWareHouseInfo(productSellData);
    }
}
