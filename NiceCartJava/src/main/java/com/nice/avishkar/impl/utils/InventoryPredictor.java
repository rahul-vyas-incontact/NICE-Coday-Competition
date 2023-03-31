package com.nice.avishkar.impl.utils;

import com.nice.avishkar.pojo.PredictedWarehouseInfo;
import com.nice.avishkar.pojo.ResourceInfo;

import java.io.IOException;

public interface InventoryPredictor {
    PredictedWarehouseInfo predictWarehouseCapacityWithProductDetails(ResourceInfo resourceInfo) throws IOException;
}
