package com.eubrunocoelho.ticketing.service.ticket;

import com.eubrunocoelho.ticketing.exception.entity.DataBindingViolationException;
import com.eubrunocoelho.ticketing.filter.ticket.TicketFilter;
import com.eubrunocoelho.ticketing.service.user.LoginUtilityService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        Category category = categoryRepository.findById(ticketUpdateDto.category())
                .orElseThrow(
                        () ->
                                new ObjectNotFoundException(
                                        "Categoria não encontrada. {id}: " + ticketUpdateDto.category()
                                )
                );

        ticketMapper.updateTicketFromDto(ticketUpdateDto, ticket, category);

        ticket.setCategory(category);

        Ticket updateTicket = ticketRepository.save(ticket);

        return ticketMapper.toDto(updateTicket);
    }

    public void deleteTicket(
            Long id
    ) {
        Ticket ticket = ticketRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new ObjectNotFoundException(
                                        "Ticket não encontrado. {id}: " + id
                                )
                );

        try {
            ticketRepository.deleteById(ticket.getId());
        } catch (Exception ex) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas.");
        }
    }

    public Page<TicketResponseDto> findAllPaged(TicketFilter filter, Pageable pageable) {
        return ticketRepository
                .findAll(filter.toSpecification(), pageable)
                .map(ticketMapper::toDto);
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
