package com.shopzy.product_service.converter;

import com.shopzy.product_service.dto.ProductDTO;
import com.shopzy.product_service.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter
{
    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO toDto(Product entity)
    {
        return modelMapper.map(entity, ProductDTO.class);
    }

    public Product toEntity(ProductDTO dto)
    {
        return modelMapper.map(dto, Product.class);
    }
}
