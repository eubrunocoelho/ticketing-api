package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.authentication.LoginUtilityService;
import com.eubrunocoelho.ticketing.dto.ticket.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.entity.Category;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.mapper.TicketMapper;
import com.eubrunocoelho.ticketing.repository.TicketRepository;
import com.eubrunocoelho.ticketing.exception.entity.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final LoginUtilityService loginUtilityService;
    private final CategoryService categoryService;
    private final TicketMapper ticketMapper;

    public TicketResponseDto createTicket(TicketCreateDto ticketCreateDto) {
        User loggedUser = loginUtilityService.getLoggedInUser();
        Category category = categoryService.findById(ticketCreateDto.category());

        Ticket ticket = ticketMapper.toEntity(ticketCreateDto, loggedUser, category);
        Ticket createdTicket = ticketRepository.save(ticket);

        return ticketMapper.toDto(createdTicket);
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Ticket não encontrado. {id}: " + id)
        );
    }
}
