package com.shopzy.product_service.service.internal.impl;

import com.shopzy.product_service.dto.ProductDTO;
import com.shopzy.product_service.entity.Product;
import com.shopzy.product_service.repository.ProductRepository;
import com.shopzy.product_service.service.internal.ProductService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProductServiceImpl
        implements ProductService
{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id)
    {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product Not Found"));
    }

    @Override
    public Product getProductByCode(String code)
    {
        return productRepository.findProductByCode(code).orElse(null);
    }

    @Override
    public Product create(Product product)
    {
        Product dbProduct = findDBProduct(product);
        if (Objects.nonNull(dbProduct)) {
            throw new EntityExistsException("Product already exists");
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product)
    {
        Product dbProduct = findDBProduct(product);
        modelMapper.map(product, dbProduct);
        return productRepository.save(dbProduct);
    }

    private Product findDBProduct(Product product) {
        Product dbProduct = null;
        if(Objects.nonNull(product.getId())) {
            dbProduct = getProductById(product.getId());
        }
        else if(Objects.nonNull(product.getCode())) {
            dbProduct = getProductByCode(product.getCode());
        }
        return dbProduct;
    }

    @Override
    public boolean deleteProduct(Long id)
    {
        try {
            Product dbProduct = getProductById(id);
            productRepository.delete(dbProduct);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
