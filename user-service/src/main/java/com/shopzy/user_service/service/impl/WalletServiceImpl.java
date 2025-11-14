package com.shopzy.user_service.service.impl;

import com.shopzy.user_service.dto.RedeemCouponDto;
import com.shopzy.user_service.entity.CouponCode;
import com.shopzy.user_service.entity.ShopzyUser;
import com.shopzy.user_service.service.CouponCodeService;
import com.shopzy.user_service.service.WalletService;
import com.shopzy.user_service.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class WalletServiceImpl
        implements WalletService
{
    @Autowired
    private AuthUtils authUtils;

    @Autowired
    private CouponCodeService couponCodeService;

    @Autowired
    private ShopzyUserService userService;

    @Override
    public String createCoupon(double amount)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authUtils.isUserAdmin(authentication)) {
            throw new RuntimeException("You don't have permissions to Create Coupons");
        }
        if (amount < 0 || amount > 100000) {
            throw new RuntimeException(String.format("Amount %d is not valid", amount));
        }
        ShopzyUser loggedInUser = authUtils.getUser(authentication);
        String coupon = UUID.randomUUID().toString();
        CouponCode couponCode = couponCodeService.create(CouponCode.builder()
                .code(coupon)
                .value(amount)
                .generatedBy(loggedInUser)
                .redeemedBy(null)
                .redeemed(false)
                .build());
        return couponCode.getCode();
    }

    @Override
    public RedeemCouponDto redeemCoupon(String code)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authUtils.isUserAdmin(authentication)) {
            throw new RuntimeException("Admin cannot redeem Coupons!");
        }
        CouponCode couponCode = couponCodeService.getByCode(code);
        if(Objects.isNull(couponCode)) {
            throw new RuntimeException("Invalid Coupon Code!");
        }
        if(couponCode.isRedeemed()) {
            throw new RuntimeException("Coupon Code Already Redeemed!");
        }
        ShopzyUser user = authUtils.getUser(authentication);
        double couponAmt = couponCode.getValue();
        user.setWalletAmt(user.getWalletAmt()+couponAmt);
        user = userService.update(user);

        couponCode.setRedeemed(true);
        couponCode.setRedeemedBy(user);
        couponCode = couponCodeService.update(couponCode);
        return RedeemCouponDto.builder()
                .message(String.format("Coupon Code worth %f redeemed for User: %s", couponAmt, user.getFullName()))
                .amount(couponAmt)
                .build();
    }


}
