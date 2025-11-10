package com.shopzy.user_service.service;

import com.shopzy.user_service.dto.LoginRequestDto;
import com.shopzy.user_service.dto.LoginResponseDto;

public interface AuthService
{
    LoginResponseDto login(LoginRequestDto requestDto);

    LoginResponseDto signup(LoginRequestDto requestDto);
}
