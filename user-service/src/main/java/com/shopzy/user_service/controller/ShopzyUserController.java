package com.shopzy.user_service.controller;

import com.shopzy.user_service.dto.LoginRequestDto;
import com.shopzy.user_service.dto.LoginResponseDto;
import com.shopzy.user_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class ShopzyUserController
{
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto requestDto)
    {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponseDto> signup(
            @RequestBody LoginRequestDto requestDto
    )
    {
        return ResponseEntity.ok(authService.signup(requestDto));
    }
}
