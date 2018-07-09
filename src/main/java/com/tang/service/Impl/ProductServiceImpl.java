package com.tang.service.Impl;

import com.tang.mapper.ProductMapper;
import com.tang.model.Product;
import com.tang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<Product> getProductList() {
        return productMapper.getProductList();
    }

    @Override
    public Product getProductById(Integer productId) {
        return productMapper.getProductById(productId);
    }
}
