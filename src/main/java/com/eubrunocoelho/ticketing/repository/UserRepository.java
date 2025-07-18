package com.eubrunocoelho.ticketing.repository;

import com.eubrunocoelho.ticketing.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
