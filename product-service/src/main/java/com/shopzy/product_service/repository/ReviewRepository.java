package com.shopzy.product_service.repository;

import com.shopzy.product_service.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository
        extends JpaRepository<Review, Long>
{
}
