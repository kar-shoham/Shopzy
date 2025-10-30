package com.shopzy.product_service.entity;

import com.shopzy.product_service.enums.Stars;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review
    extends BaseEntity
{
    private String title;

    private String description;

    private Stars reviewStars;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
