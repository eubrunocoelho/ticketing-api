package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.dto.tickets.TicketCreateDto;
import com.eubrunocoelho.ticketing.entity.Categories;
import com.eubrunocoelho.ticketing.entity.Tickets;
import com.eubrunocoelho.ticketing.entity.Users;
import com.eubrunocoelho.ticketing.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final LoginUtilityService loginUtilityService;
    private final CategoryService categoryService;

    public Tickets createTicket(TicketCreateDto ticketCreateDto) {
        Users loggedUser = loginUtilityService.getLoggedInUser();
        Categories category = categoryService.findById(ticketCreateDto.categoryId());

        Tickets ticket = new Tickets();

        ticket.setUser(loggedUser);
        ticket.setTitle(ticketCreateDto.title());
        ticket.setDescription(ticketCreateDto.description());
        ticket.setStatus(Tickets.Status.OPEN);
        ticket.setCategory(category);

        return ticketRepository.save(ticket);
    }
}
