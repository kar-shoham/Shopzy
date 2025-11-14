package com.shopzy.user_service.utils;

import com.shopzy.user_service.dto.JwtResponse;
import com.shopzy.user_service.entity.ShopzyUser;
import com.shopzy.user_service.enums.UserType;
import com.shopzy.user_service.service.impl.ShopzyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils
{
    @Autowired
    private ShopzyUserService userService;

    public boolean isUserAdmin(@NonNull Authentication authentication) {
        return getUserType(authentication).equals(UserType.ADMIN);
    }

    public boolean isUserUser(@NonNull Authentication authentication) {
        return getUserType(authentication).equals(UserType.USER);
    }

    public UserType getUserType(@NonNull Authentication authentication) {
        JwtResponse jwtResponse = (JwtResponse) authentication.getPrincipal();
        return jwtResponse.getRole();
    }

    public Long getUserId(@NonNull Authentication authentication) {
        JwtResponse jwtResponse = (JwtResponse) authentication.getPrincipal();
        return jwtResponse.getId();
    }

    public ShopzyUser getUser(@NonNull Authentication authentication) {
        Long userId = getUserId(authentication);
        return userService.findById(userId);
    }
}
