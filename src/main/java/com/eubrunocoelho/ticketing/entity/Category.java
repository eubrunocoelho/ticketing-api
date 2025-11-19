package com.eubrunocoelho.ticketing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table( name = "categories" )
@EntityListeners( AuditingEntityListener.class )
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
public class Category
{
    public enum Priority
    {
        /**
         * Baixa prioridade
         */
        @SuppressWarnings( "checkstyle:UnusedVariable" )
        LOW,

        /**
         * Média prioridade
         */
        @SuppressWarnings( "checkstyle:UnusedVariable" )
        MEDIUM,

        /**
         * Alta prioridade
         */
        @SuppressWarnings( "checkstyle:UnusedVariable" )
        HIGH
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", nullable = false )
    @EqualsAndHashCode.Include
    Long id;

    @Column( name = "name", nullable = false, unique = true )
    String name;

    @Column( name = "description", nullable = true )
    String description;

    @Enumerated( EnumType.STRING )
    @Column( name = "priority", nullable = false )
    Priority priority;
}
