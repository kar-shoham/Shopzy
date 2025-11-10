package com.shopzy.user_service.service.impl;

import com.shopzy.user_service.entity.ShopzyUser;
import com.shopzy.user_service.repository.ShopzyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    public ShopzyUser create(ShopzyUser user)
    {
        ShopzyUser dbUser = (ShopzyUser) loadUserByUsername(user.getUsername());
        if(Objects.nonNull(dbUser)) {
            throw new RuntimeException("Username already exists!");
        }
        return repository.save(user);
    }
}
