package com.shopzy.product_service.service.internal;

import com.shopzy.product_service.entity.Product;

import java.util.List;

public interface ProductService
{
    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product getProductByCode(String code);

    Product addOrUpdate(Product product);

    boolean deleteProduct(Long id);
}
