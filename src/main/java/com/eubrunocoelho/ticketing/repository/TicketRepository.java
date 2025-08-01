package com.eubrunocoelho.ticketing.repository;

import com.eubrunocoelho.ticketing.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
