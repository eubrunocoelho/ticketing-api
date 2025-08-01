package com.eubrunocoelho.ticketing.repository;

import com.eubrunocoelho.ticketing.entity.TicketReplies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketReplyRepository extends JpaRepository<TicketReplies, Long> {
}
