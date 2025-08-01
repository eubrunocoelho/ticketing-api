package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.dto.category.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.Categories;
import com.eubrunocoelho.ticketing.entity.Tickets;
import com.eubrunocoelho.ticketing.entity.Users;
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

    public TicketResponseDto createTicket(TicketCreateDto ticketCreateDto) {
        Users loggedUser = loginUtilityService.getLoggedInUser();
        Categories category = categoryService.findById(ticketCreateDto.category());

        Tickets ticket = new Tickets();

        ticket.setUser(loggedUser);
        ticket.setTitle(ticketCreateDto.title());
        ticket.setContent(ticketCreateDto.content());
        ticket.setStatus(Tickets.Status.OPEN);
        ticket.setCategory(category);

        Tickets createdTicket = ticketRepository.save(ticket);

        UserResponseDto userResponseDto = new UserResponseDto(
                createdTicket.getUser().getId(),
                createdTicket.getUser().getUsername(),
                createdTicket.getUser().getEmail(),
                createdTicket.getUser().getUsername()
        );

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(
                createdTicket.getCategory().getId(),
                createdTicket.getCategory().getName(),
                createdTicket.getCategory().getDescription(),
                createdTicket.getCategory().getPriority().name()
        );

        return new TicketResponseDto(
                ticket.getId(),
                userResponseDto,
                categoryResponseDto,
                ticket.getTitle(),
                ticket.getContent(),
                ticket.getStatus().name()
        );
    }

    public Tickets findById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Ticket não encontrado. {id}: " + id)
        );
    }
}
