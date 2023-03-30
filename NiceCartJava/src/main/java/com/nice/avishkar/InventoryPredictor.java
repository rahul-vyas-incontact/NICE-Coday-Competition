package com.nice.avishkar;

import java.io.IOException;
import java.nio.file.Path;

public interface InventoryPredictor {
    PredictedWarehouseInfo predictWarehouseCapacityWithProductDetails(ResourceInfo resourceInfo) throws IOException;
}
