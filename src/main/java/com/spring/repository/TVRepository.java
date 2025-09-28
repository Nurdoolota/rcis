package com.spring.repository;

import com.spring.entity.TV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TVRepository extends JpaRepository<TV, Long> {
    List<TV> findByPriceGreaterThan(Double price);

    Page<TV> findAll(Pageable pageable); // Добавьте это, если нужно
}