package com.shopzy.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO
{
    private Long id;
    private String name;
    private String code;
    private int quantity;
    private double price;
}
