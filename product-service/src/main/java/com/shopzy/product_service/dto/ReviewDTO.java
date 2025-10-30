package com.shopzy.product_service.dto;

import com.shopzy.product_service.enums.Stars;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO
{
    private Long id;
    private String title;
    private String description;
    private Stars reviewStars;
    private Long productId;
}
