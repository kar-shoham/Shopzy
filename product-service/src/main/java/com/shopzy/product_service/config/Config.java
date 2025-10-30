package com.shopzy.product_service.config;

import com.shopzy.product_service.entity.Product;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config
{
    @Bean
    protected ModelMapper modelMapper()
    {
        ModelMapper mapper = new ModelMapper();
        TypeMap<Product, Product> productProductTypeMap = mapper.createTypeMap(Product.class, Product.class);
        productProductTypeMap.setPropertyCondition(Conditions.isNotNull());
        return mapper;
    }
}
