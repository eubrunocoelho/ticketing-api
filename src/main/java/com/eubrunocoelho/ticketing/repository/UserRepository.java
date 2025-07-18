package com.eubrunocoelho.ticketing.repository;

import com.eubrunocoelho.ticketing.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    public boolean existsByEmail(String email);

    public boolean existsByUsername(String username);

    public List<Users> findByUsername(String username);
}
