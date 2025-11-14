package com.shopzy.user_service.service;

import com.shopzy.user_service.dto.RedeemCouponDto;

public interface WalletService
{
    String createCoupon(double amount);

    RedeemCouponDto redeemCoupon(String code);
}
