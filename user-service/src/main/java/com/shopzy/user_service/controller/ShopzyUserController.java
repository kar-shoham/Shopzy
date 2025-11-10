package com.shopzy.user_service.controller;

import com.shopzy.user_service.dto.LoginRequestDto;
import com.shopzy.user_service.dto.LoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class ShopzyUserController
{
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto requestDto)
    {
        return null;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup()
    {
        return null;
    }
}
