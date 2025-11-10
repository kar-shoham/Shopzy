package com.shopzy.user_service.repository;

import com.shopzy.user_service.entity.ShopzyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopzyUserRepository
        extends JpaRepository<ShopzyUser, Long>
{
    Optional<ShopzyUser> findByUsername(String username);
}
