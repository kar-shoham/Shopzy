package com.shopzy.user_service.config;

import com.shopzy.user_service.entity.CouponCode;
import com.shopzy.user_service.entity.ShopzyUser;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig
{
    @Bean
    protected ModelMapper modelMapper()
    {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<ShopzyUser, ShopzyUser> userUserTypeMap = modelMapper.typeMap(ShopzyUser.class, ShopzyUser.class);
        userUserTypeMap.setPropertyCondition(Conditions.isNotNull());

        TypeMap<CouponCode, CouponCode> couponCodeCouponCodeTypeMap = modelMapper.typeMap(CouponCode.class, CouponCode.class);
        couponCodeCouponCodeTypeMap.setPropertyCondition(Conditions.isNotNull());

        return modelMapper;
    }
}
