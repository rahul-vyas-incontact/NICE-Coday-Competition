package com.nice.avishkar;

import com.nice.avishkar.impl.utils.InventoryPredictor;
import com.nice.avishkar.pojo.PredictedWarehouseInfo;
import com.nice.avishkar.pojo.ResourceInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InventoryPredictorTests {

    @InjectMocks
    private static PredictedWarehouseInfo resultData;

    private ResourceInfo resourceInfo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void test1() throws IOException {

        Path productInfoFilePath = Paths.get("src/main/resources/ProductInfo.csv");
        Path purchaseHistory1FilePath = Paths.get("src/main/resources/Day_1_PurchaseHistory.csv");
        Path purchaseHistory2FilePath = Paths.get("src/main/resources/Day_2_PurchaseHistory.csv");
        Path purchaseHistory3FilePath = Paths.get("src/main/resources/Day_3_PurchaseHistory.csv");
        Path purchaseHistory4FilePath = Paths.get("src/main/resources/Day_4_PurchaseHistory.csv");
        resourceInfo = new ResourceInfo(productInfoFilePath, purchaseHistory1FilePath, purchaseHistory2FilePath, purchaseHistory3FilePath, purchaseHistory4FilePath);
        InventoryPredictor predictor = new InventoryPredictorImpl();
        resultData = predictor.predictWarehouseCapacityWithProductDetails(resourceInfo);

        //Warehouse Capacity
        Assert.assertTrue(resultData.getWarehouseCapacity() == 1043);
        Assert.assertTrue(resultData.getProductList().get(0).getProductId() == 10);
        Assert.assertTrue(resultData.getProductList().get(0).getPredictedQuantity() == 135);

        // Highest Quantity Product
        Assert.assertTrue(resultData.getProductList().get(14).getProductId() == 15);
        Assert.assertTrue(resultData.getProductList().get(14).getPredictedQuantity() == 0);

        // Least Quantity Product
        Assert.assertNotNull(resultData.getProductList().stream().filter(p -> p.getProductId() == 13));
        Assert.assertTrue(resultData.getProductList().stream().filter(p -> p.getProductId() == 13).findFirst().get().getPredictedQuantity() == 53);

    }
}
