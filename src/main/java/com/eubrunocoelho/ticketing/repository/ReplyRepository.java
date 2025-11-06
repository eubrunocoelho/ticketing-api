package com.eubrunocoelho.ticketing.repository;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long>, JpaSpecificationExecutor<Reply>
{
    boolean existsByTicketId( Long ticketId );

    Optional<Reply> findByTicketIdAndId( Long ticketId, Long id );

    Optional<Reply> findTopByTicketIdOrderByCreatedAtDesc( Long ticketId );

    Optional<Reply> findTopByTicketIdAndCreatedUserRoleOrderByCreatedAtDesc( Long ticketId, User.Role role );

    List<Reply> findByTicketIdOrderByCreatedAtDesc( Long ticketId );
}
