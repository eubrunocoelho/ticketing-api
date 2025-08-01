package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.dto.category.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.Category;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
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
        User loggedUser = loginUtilityService.getLoggedInUser();
        Category category = categoryService.findById(ticketCreateDto.category());

        Ticket ticket = new Ticket();

        ticket.setUser(loggedUser);
        ticket.setTitle(ticketCreateDto.title());
        ticket.setContent(ticketCreateDto.content());
        ticket.setStatus(Ticket.Status.OPEN);
        ticket.setCategory(category);

        Ticket createdTicket = ticketRepository.save(ticket);

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

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Ticket não encontrado. {id}: " + id)
        );
    }
}
