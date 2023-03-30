package com.nice.avishkar;

import java.nio.file.Path;

public class ResourceInfo {

    Path productInfoPath;
    Path day1HistoryPath;
    Path day2HistoryPath;
    Path day3HistoryPath;
    Path day4HistoryPath;

    public ResourceInfo(Path productInfoPath, Path day1HistoryPath, Path day2HistoryPath, Path day3HistoryPath, Path day4HistoryPath) {
        this.productInfoPath = productInfoPath;
        this.day1HistoryPath = day1HistoryPath;
        this.day2HistoryPath = day2HistoryPath;
        this.day3HistoryPath = day3HistoryPath;
        this.day4HistoryPath = day4HistoryPath;
    }
}
