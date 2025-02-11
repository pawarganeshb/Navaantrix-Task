package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.SubProduct;

public interface SubProductRepository extends JpaRepository<SubProduct, Long> {
}
