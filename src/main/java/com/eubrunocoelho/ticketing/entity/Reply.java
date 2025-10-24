package com.eubrunocoelho.ticketing.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table( name = "replies" )
@EntityListeners( AuditingEntityListener.class )
public class Reply
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", nullable = false )
    private Long id;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "ticket_id", nullable = false )
    @JsonBackReference
    private Ticket ticket;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "created_user_id", nullable = false )
    private User createdUser;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "responded_to_user_id", nullable = false )
    private User respondedToUser;

    @ManyToOne( fetch = FetchType.LAZY, optional = true )
    @JoinColumn( name = "parent_id", nullable = true )
    private Reply parent;

    @Column( name = "content", columnDefinition = "TEXT", nullable = false )
    private String content;

    @CreatedDate
    @Column( name = "created_at", nullable = false, updatable = false )
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column( name = "updated_at", nullable = false )
    private LocalDateTime updatedAt;
}
