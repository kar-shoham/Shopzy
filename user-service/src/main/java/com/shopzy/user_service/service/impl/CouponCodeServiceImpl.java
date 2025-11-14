package com.shopzy.user_service.service.impl;

import com.shopzy.user_service.entity.CouponCode;
import com.shopzy.user_service.repository.CouponCodeRepository;
import com.shopzy.user_service.service.CouponCodeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class CouponCodeServiceImpl
        implements CouponCodeService
{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CouponCodeRepository couponCodeRepository;

    @Override
    public CouponCode getByCode(String code)
    {
        return couponCodeRepository.findByCode(code).orElse(null);
    }

    @Override
    public CouponCode create(CouponCode couponCode)
    {
        if(couponCode.getValue() < 0 || couponCode.getValue() > 100000) {
            throw new RuntimeException(String.format("Amount %d is not valid", couponCode.getValue()));
        }
        CouponCode dbCouponCode = getByCode(couponCode.getCode());
        if(Objects.nonNull(dbCouponCode)) {
            throw new RuntimeException("Coupon Code Already Exists!");
        }
        couponCode.setRedeemedBy(null);
        couponCode.setRedeemed(false);
        return couponCodeRepository.save(couponCode);
    }

    @Override
    public CouponCode update(CouponCode couponCode)
    {
        CouponCode dbCouponCode = getByCode(couponCode.getCode());
        if(Objects.isNull(dbCouponCode)) {
            throw new RuntimeException("Coupon Code does not Exist!");
        }
        modelMapper.map(couponCode, dbCouponCode);
        return couponCodeRepository.save(dbCouponCode);
    }
}
