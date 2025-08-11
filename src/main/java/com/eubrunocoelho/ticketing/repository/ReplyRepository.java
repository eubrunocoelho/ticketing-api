package com.eubrunocoelho.ticketing.repository;

import com.eubrunocoelho.ticketing.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    boolean existsByTicketId(Long ticketId);

    Optional<Reply> findByTicketIdAndId(Long ticketId, Long id);

    Optional<Reply> findTopByTicketIdOrderByCreatedAtDesc(Long ticketId);

    List<Reply> findByTicketIdOrderByCreatedAtDesc(long ticketId);
}
