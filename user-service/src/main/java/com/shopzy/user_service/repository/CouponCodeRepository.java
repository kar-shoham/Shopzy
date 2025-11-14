package com.shopzy.user_service.repository;

import com.shopzy.user_service.entity.CouponCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponCodeRepository
    extends JpaRepository<CouponCode, Long>
{
    Optional<CouponCode> findByCode(String code);
}
