package com.tang.mapper;

import com.tang.model.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> getProductList();

    Product getProductById(String productId);
}
