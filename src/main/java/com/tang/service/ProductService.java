package com.tang.service;

import com.tang.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductList();

    Product getProductById(Integer productId);
}
