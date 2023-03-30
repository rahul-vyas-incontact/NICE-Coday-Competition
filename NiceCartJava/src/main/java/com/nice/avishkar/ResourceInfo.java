package com.nice.avishkar;

import java.nio.file.Path;

public class ResourceInfo {

    Path productInfoPath;
    Path day1HistoryPath;
    Path day2HistoryPath;
    Path day3HistoryPath;
    Path day4HistoryPath;

    public Path getProductInfoPath() {
        return productInfoPath;
    }

    public Path getDay1HistoryPath() {
        return day1HistoryPath;
    }

    public Path getDay2HistoryPath() {
        return day2HistoryPath;
    }

    public Path getDay3HistoryPath() {
        return day3HistoryPath;
    }

    public Path getDay4HistoryPath() {
        return day4HistoryPath;
    }


    public ResourceInfo(Path productInfoPath, Path day1HistoryPath, Path day2HistoryPath, Path day3HistoryPath, Path day4HistoryPath) {
        this.productInfoPath = productInfoPath;
        this.day1HistoryPath = day1HistoryPath;
        this.day2HistoryPath = day2HistoryPath;
        this.day3HistoryPath = day3HistoryPath;
        this.day4HistoryPath = day4HistoryPath;
    }
}
