package com.shopzy.user_service.entity;

import jakarta.persistence.Column;
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
public class CouponCode
    extends BaseEntity
{
    @Column(nullable = false, updatable = false)
    private String code;

    @Column(nullable = false)
    private double value;

    @ManyToOne
    @JoinColumn(name = "generated_by_user_id", nullable = false, updatable = false)
    private ShopzyUser generatedBy;

    @ManyToOne
    @JoinColumn(name = "redeemed_by_user_id")
    private ShopzyUser redeemedBy;

    private boolean redeemed;
}
