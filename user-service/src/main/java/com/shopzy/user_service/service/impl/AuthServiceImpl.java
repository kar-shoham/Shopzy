package com.shopzy.user_service.service.impl;

import com.shopzy.user_service.dto.LoginRequestDto;
import com.shopzy.user_service.dto.LoginResponseDto;
import com.shopzy.user_service.entity.ShopzyUser;
import com.shopzy.user_service.enums.UserType;
import com.shopzy.user_service.service.AuthService;
import com.shopzy.user_service.utils.JwtUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl
        implements AuthService
{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ShopzyUserService userService;

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto)
    {
        Authentication authentication = new
                UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword());
        Authentication res = authenticationManager.authenticate(authentication);
        ShopzyUser user = (ShopzyUser) res.getPrincipal();
        String jwt = jwtUtils.generateJwt(user);
        return LoginResponseDto.builder()
                .username(user.getUsername())
                .fullName(user.getFullName())
                .token(jwt)
                .build();
    }

    @Override
    public LoginResponseDto signup(LoginRequestDto requestDto)
    {
        ShopzyUser user = (ShopzyUser) userService.loadUserByUsername(requestDto.getUsername());
        if(Objects.nonNull(user)) {
            throw new RuntimeException("Username already exists!");
        }
        user = ShopzyUser.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .fullName(requestDto.getFullName())
                .userType(UserType.USER)
                .walletAmt(1000.0)
                .build();

        user = userService.create(user);
        String jwt = jwtUtils.generateJwt(user);
        return LoginResponseDto.builder()
                .username(user.getUsername())
                .fullName(user.getFullName())
                .token(jwt)
                .build();
    }
}
