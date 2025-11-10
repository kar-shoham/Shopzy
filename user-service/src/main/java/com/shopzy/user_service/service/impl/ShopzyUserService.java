package com.shopzy.user_service.service.impl;

import com.shopzy.user_service.repository.ShopzyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ShopzyUserService
        implements UserDetailsService
{
    @Autowired
    private ShopzyUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException
    {
        return repository.findByUsername(username).orElse(null);
    }
}
