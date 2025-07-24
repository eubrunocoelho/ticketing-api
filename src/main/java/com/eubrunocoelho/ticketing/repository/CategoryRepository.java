package com.eubrunocoelho.ticketing.repository;

import com.eubrunocoelho.ticketing.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categories, Long> {
}
