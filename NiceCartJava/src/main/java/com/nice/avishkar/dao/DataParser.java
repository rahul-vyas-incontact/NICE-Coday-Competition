package com.nice.avishkar.dao;

import com.nice.avishkar.pojo.Product;
import com.nice.avishkar.pojo.ResourceInfo;

import java.io.FileNotFoundException;
import java.util.Map;

public interface DataParser {

    Map<String, Product> parse(ResourceInfo resourceInfo) throws FileNotFoundException;

}
