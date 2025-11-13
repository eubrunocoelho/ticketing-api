package com.eubrunocoelho.ticketing.repository;

import com.eubrunocoelho.ticketing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>
{
    boolean existsByEmail( String email );

    boolean existsByUsername( String username );

    Optional<User> findByUsernameOrEmail( String username, String email );
}
