package com.eubrunocoelho.ticketing.repository;

import com.eubrunocoelho.ticketing.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    boolean existsByTicketId(Long ticketId);
}
