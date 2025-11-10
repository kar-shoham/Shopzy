package com.shopzy.user_service.service.impl;

import com.shopzy.user_service.dto.LoginRequestDto;
import com.shopzy.user_service.dto.LoginResponseDto;
import com.shopzy.user_service.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl
        implements AuthService
{
    @Override
    public LoginResponseDto login(LoginRequestDto requestDto)
    {
        return null;
    }

    @Override
    public LoginRequestDto signup(LoginRequestDto requestDto)
    {
        return null;
    }
}
