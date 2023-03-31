package com.nice.avishkar.dao;

import com.nice.avishkar.impl.utils.Constants;
import com.nice.avishkar.pojo.Product;
import com.nice.avishkar.pojo.ResourceInfo;
import com.nice.avishkar.pojo.SellDayHistory;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class CSVDataParser implements DataParser {

    @Override
    public Map<String, Product> getProductSellData(ResourceInfo resourceInfo) {
        Map<String, Product> productEntities = parseProductCSV(resourceInfo);
        List<Path> historyPaths = Arrays.asList(resourceInfo.getDay1HistoryPath(), resourceInfo.getDay2HistoryPath(),
                resourceInfo.getDay3HistoryPath(), resourceInfo.getDay4HistoryPath());
        int day = 1;
        for (Path history : historyPaths) {
            setPeriodicalHistory(day, productEntities, history);
            day++;
        }
        return productEntities;
    }


    private void setPeriodicalHistory(int day, Map<String, Product> productEntities, Path sellHistory) {
        Map<String, List<SellDayHistory>> productSellMap = parseSellHistoryProductCSV(sellHistory);
        for (String prodId : productEntities.keySet()) {
            List<SellDayHistory> sellDayHistories = productSellMap.get(prodId);
            Map<Integer, List<SellDayHistory>> sellDayHistoryMap = productEntities.get(prodId).getSellDayHistoryMap();
            if (sellDayHistoryMap == null) {
                sellDayHistoryMap = new HashMap<>(Constants.HISTORY_PERIOD_IN_DAY);
            }
            sellDayHistoryMap.put(day, sellDayHistories);
            productEntities.get(prodId).setSellDayHistoryMap(sellDayHistoryMap);
        }
    }

    private Map<String, Product> parseProductCSV(ResourceInfo resourceInfo) {
        Map<String, Product> productEntities = new HashMap<>();
        try {
            CSVReader reader = buildCSVParser(resourceInfo.getProductInfoPath().toString());
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                Product product = new Product();
                product.setProductId(lineInArray[0]);
                product.setProductName(lineInArray[1]);
                product.setBuyPrice(Long.parseLong(lineInArray[2]));
                product.setSellPrice(Long.parseLong(lineInArray[3]));
                product.setQuantity(Long.parseLong(lineInArray[Constants.HISTORY_PERIOD_IN_DAY]));
                product.setProfitMargin(calculateMargin(product));
                productEntities.putIfAbsent(product.getProductId(), product);
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        return productEntities;
    }

    private Map<String, List<SellDayHistory>> parseSellHistoryProductCSV(Path resourceInfo) {
        Map<String, List<SellDayHistory>> productSellsMap = new HashMap<>();
        List<SellDayHistory> sellDayHistories = new ArrayList<>();
        try {
            CSVReader reader = buildCSVParser(resourceInfo.toString());
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                if (!StringUtils.isEmpty(lineInArray[Constants.HISTORY_PERIOD_IN_DAY])) {
                    SellDayHistory sellDayHistory = new SellDayHistory();
                    sellDayHistory.setPurchaseHistoryId(lineInArray[0]);
                    sellDayHistory.setProductId(lineInArray[1]);
                    sellDayHistory.setSellQuantity(Long.parseLong(lineInArray[2]));
                    sellDayHistory.setSellPrice(Long.parseLong(lineInArray[3]));
                    sellDayHistory.setTimeOfTheDay(lineInArray[Constants.HISTORY_PERIOD_IN_DAY]);
                    sellDayHistories.add(sellDayHistory);
                }
            }
            productSellsMap = sellDayHistories.
                    stream().collect(Collectors.groupingBy(SellDayHistory::getProductId, toList()));

        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        return productSellsMap;
    }

    private CSVReader buildCSVParser(String resourcePath) throws FileNotFoundException {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(Constants.SEPARATOR).build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(resourcePath)
        ).withCSVParser(csvParser).withSkipLines(1).build();
        return reader;
    }

    private BigDecimal calculateMargin(Product product) {
        double profit = Math.subtractExact(product.getSellPrice(), product.getBuyPrice());
        return BigDecimal.valueOf(profit / product.getBuyPrice()).multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.CEILING);
    }

}
