package com.eubrunocoelho.ticketing.repository;

import com.eubrunocoelho.ticketing.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
