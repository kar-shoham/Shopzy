package com.shopzy.user_service.controller;

import com.shopzy.user_service.dto.RedeemCouponDto;
import com.shopzy.user_service.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController
{
    @Autowired
    private WalletService walletService;

    @PostMapping("/create_coupon/{amount}")
    public ResponseEntity<String> createCoupon(@PathVariable("amount") double amount)
    {
        return ResponseEntity.ok(walletService.createCoupon(amount));
    }

    @PostMapping("redeem_coupon/{code}")
    public ResponseEntity<RedeemCouponDto> redeemCoupon(@PathVariable("code") String code){
        return ResponseEntity.ok(walletService.redeemCoupon(code));
    }
}
