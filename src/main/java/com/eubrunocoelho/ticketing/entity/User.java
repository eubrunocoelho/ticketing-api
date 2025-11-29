package com.eubrunocoelho.ticketing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@Table( name = "users" )
@EntityListeners( AuditingEntityListener.class )
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
public class User
{
    public enum Role
    {
        /**
         * Função de administrador
         */
        @SuppressWarnings( "checkstyle:UnusedVariable" )
        ROLE_ADMIN,

        /**
         * Função de staff
         */
        @SuppressWarnings( "checkstyle:UnusedVariable" )
        ROLE_STAFF,

        /**
         * Função de usuário
         */
        @SuppressWarnings( "checkstyle:UnusedVariable" )
        ROLE_USER
    }

    public enum Status
    {
        /**
         * Usuário ativo
         */
        @SuppressWarnings( "checkstyle:UnusedVariable" )
        ACTIVE,

        /**
         * Usuário inativo
         */
        @SuppressWarnings( "checkstyle:UnusedVariable" )
        INACTIVE
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", nullable = false )
    @EqualsAndHashCode.Include
    Long id;

    @Column( name = "email", nullable = false, unique = true )
    String email;

    @Column( name = "username", nullable = false, unique = true )
    String username;

    @Column( name = "password", nullable = false )
    String password;

    @Enumerated( EnumType.STRING )
    @Column( name = "role", nullable = false )
    Role role;

    @Enumerated( EnumType.STRING )
    @Column( name = "status", nullable = false )
    Status status;

    @CreatedDate
    @Column( name = "created_at", nullable = false, updatable = false )
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column( name = "updated_at", nullable = false )
    private LocalDateTime updatedAt;
}
