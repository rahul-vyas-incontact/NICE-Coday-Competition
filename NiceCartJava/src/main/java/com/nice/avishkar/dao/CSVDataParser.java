package com.nice.avishkar.dao;

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
    public Map<String, Product> parse(ResourceInfo resourceInfo) throws FileNotFoundException {
        Map<String, Product> productEntities = parseProductCSV(resourceInfo);
        List<Path> historyPaths = Arrays.asList(resourceInfo.getDay1HistoryPath(), resourceInfo.getDay2HistoryPath(),
                                                resourceInfo.getDay3HistoryPath(), resourceInfo.getDay4HistoryPath());
        int day = 1;
        for(Path history : historyPaths) {
            setPeriodicalHistory(day, productEntities, history);
            day++;
        }
        return productEntities;
    }


    private void setPeriodicalHistory(int day, Map<String, Product> productEntities, Path sellHistory) throws FileNotFoundException {
        Map<String, List<SellDayHistory>> productSellMap = parseSellHistoryProductCSV(sellHistory);
        for (String prodId : productEntities.keySet()) {
            List<SellDayHistory> sellDayHistories = productSellMap.get(prodId);
            Map<Integer, List<SellDayHistory>> sellDayHistoryMap = productEntities.get(prodId).getSellDayHistoryMap();
            if(sellDayHistoryMap == null) {
                sellDayHistoryMap = new HashMap<>();
            }
            sellDayHistoryMap.put(day, sellDayHistories);
            productEntities.get(prodId).setSellDayHistoryMap(sellDayHistoryMap);
        }
    }

    private Map<String, Product> parseProductCSV(ResourceInfo resourceInfo) throws FileNotFoundException {
        //resourceInfo
        Map<String, Product> productEntities = new HashMap<>();
        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
            CSVReader reader = new CSVReaderBuilder(new FileReader(resourceInfo.getProductInfoPath().toString())
            ).withCSVParser(csvParser).withSkipLines(1).build();
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                Product product = new Product();
                product.setProductId(lineInArray[0]);
                product.setProductName(lineInArray[1]);
                product.setBuyPrice(Long.parseLong(lineInArray[2]));
                product.setSellPrice(Long.parseLong(lineInArray[3]));
                product.setQuantity(Long.parseLong(lineInArray[4]));
                product.setProfitMargin(calculateMargin(product));
                productEntities.putIfAbsent(product.getProductId(), product);
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productEntities;
    }

    private Map<String, List<SellDayHistory>> parseSellHistoryProductCSV(Path resourceInfo) throws FileNotFoundException {
        Map<String, List<SellDayHistory>> productSellsMap = new HashMap<>();
        List<SellDayHistory> sellDayHistories = new ArrayList<>();
        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
            CSVReader reader = new CSVReaderBuilder(new FileReader(resourceInfo.toString())
            ).withCSVParser(csvParser).withSkipLines(1).build();
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                if (!StringUtils.isEmpty(lineInArray[4])) {
                    SellDayHistory sellDayHistory = new SellDayHistory();
                    sellDayHistory.setPurchaseHistoryId(lineInArray[0]);
                    sellDayHistory.setProductId(lineInArray[1]);
                    sellDayHistory.setSellQuantity(Long.parseLong(lineInArray[2]));
                    sellDayHistory.setSellPrice(Long.parseLong(lineInArray[3]));
                    sellDayHistory.setTimeOfTheDay(lineInArray[4]);
                    sellDayHistories.add(sellDayHistory);
                }
            }
            productSellsMap = sellDayHistories.
                    stream().collect(Collectors.groupingBy(SellDayHistory::getProductId, toList()));

        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productSellsMap;
    }

    private BigDecimal calculateMargin(Product product) {
        double profit = Math.subtractExact(product.getSellPrice(), product.getBuyPrice());
        return BigDecimal.valueOf(profit / product.getBuyPrice()).multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.CEILING);
    }

}
