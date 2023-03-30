package com.nice.avishkar.utils;

import com.nice.avishkar.Product;
import com.nice.avishkar.ResourceInfo;

import java.io.FileNotFoundException;
import java.util.Map;

public interface DataParser {

    Map<String, Product> parse(ResourceInfo resourceInfo) throws FileNotFoundException;

}
