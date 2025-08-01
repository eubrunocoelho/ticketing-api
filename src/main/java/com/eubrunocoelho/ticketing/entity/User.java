package com.eubrunocoelho.ticketing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    public static enum Role {
        ROLE_ADMIN, ROLE_STAFF, ROLE_USER;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "username", nullable = false, unique = true)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    Role role;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
