package com.shopzy.user_service.service;

import com.shopzy.user_service.entity.CouponCode;

public interface CouponCodeService
{
    CouponCode getByCode(String code);

    CouponCode create(CouponCode couponCode);

    CouponCode update(CouponCode couponCode);
}
