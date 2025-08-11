package com.eubrunocoelho.ticketing.service.ticket;

import com.eubrunocoelho.ticketing.authentication.LoginUtilityService;
import com.eubrunocoelho.ticketing.dto.ticket.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketUpdateDto;
import com.eubrunocoelho.ticketing.entity.Category;
import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.mapper.TicketMapper;
import com.eubrunocoelho.ticketing.repository.CategoryRepository;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import com.eubrunocoelho.ticketing.repository.TicketRepository;
import com.eubrunocoelho.ticketing.exception.entity.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final LoginUtilityService loginUtilityService;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final CategoryRepository categoryRepository;
    private final ReplyRepository replyRepository;

    public TicketResponseDto createTicket(TicketCreateDto ticketCreateDto) {
        User loggedUser = loginUtilityService.getLoggedInUser();
        Category category = categoryRepository.findById(ticketCreateDto.category())
                .orElseThrow(
                        () ->
                                new ObjectNotFoundException(
                                        "Categoria não encontrada. {id}: "
                                                + ticketCreateDto.category()
                                )
                );

        Ticket ticket = ticketMapper.toEntity(ticketCreateDto, loggedUser, category);
        Ticket createdTicket = ticketRepository.save(ticket);

        return ticketMapper.toDto(createdTicket);
    }

    public TicketResponseDto updateTicket(
            Long id,
            TicketUpdateDto ticketUpdateDto
    ) {
        Ticket ticket = ticketRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new ObjectNotFoundException(
                                        "Ticket não encontrado. {id}: " + id
                                )
                );

        ticketMapper.updateTicketFromDto(ticketUpdateDto, ticket);
        Ticket updateTicket = ticketRepository.save(ticket);

        return ticketMapper.toDto(updateTicket);
    }

    public List<TicketResponseDto> findAll() {
        return ticketRepository
                .findAll()
                .stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    public TicketResponseDto findById(Long id) {
        Ticket ticket = ticketRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new ObjectNotFoundException(
                                        "Ticket não encontrado. {id}: " + id
                                )
                );

        List<Reply> replies = replyRepository
                .findByTicketIdOrderByCreatedAtDesc(ticket.getId());

        ticket.setReplies(replies);

        return ticketMapper.toDtoWithReplies(ticket);
    }
}
