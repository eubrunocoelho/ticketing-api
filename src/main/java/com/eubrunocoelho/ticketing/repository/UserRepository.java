package com.eubrunocoelho.ticketing.repository;

import com.eubrunocoelho.ticketing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsByEmail(String email);

    public boolean existsByUsername(String username);

    public Optional<User> findByUsername(String username);
}
