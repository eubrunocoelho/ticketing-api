package com.eubrunocoelho.ticketing.repository;

import com.eubrunocoelho.ticketing.entity.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Tickets, Long> {
}
