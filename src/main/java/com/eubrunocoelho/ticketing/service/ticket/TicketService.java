package com.eubrunocoelho.ticketing.service.ticket;

import com.eubrunocoelho.ticketing.dto.ticket.TicketFilterDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketStatusUpdateDto;
import com.eubrunocoelho.ticketing.exception.entity.DataBindingViolationException;
import com.eubrunocoelho.ticketing.repository.specification.TicketSpecificationBuilder;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class TicketService
{
    private final UserPrincipalService userPrincipalService;
    private final TicketRepository ticketRepository;
    private final TicketSpecificationBuilder ticketSpecificationBuilder;
    private final TicketMapper ticketMapper;
    private final CategoryRepository categoryRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public TicketResponseDto createTicket( @Valid TicketCreateDto ticketCreateDto )
    {
        User loggedUser = userPrincipalService.getLoggedInUser();
        Category category = categoryRepository.findById( ticketCreateDto.category() )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Categoria não encontrada. {id}: "
                                        + ticketCreateDto.category()
                        )
                );

        Ticket ticket = ticketMapper.toEntity( ticketCreateDto, loggedUser, category );
        Ticket createdTicket = ticketRepository.save( ticket );

        return ticketMapper.toDto( createdTicket );
    }

    @Transactional( readOnly = true )
    public TicketResponseDto findById( Long id )
    {
        Ticket ticket = ticketRepository
                .findById( id )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Ticket não encontrado. {id}: " + id
                        )
                );

        List<Reply> replies = replyRepository
                .findByTicketIdOrderByCreatedAtDesc( ticket.getId() );

        return ( replies.isEmpty() )
                ? ticketMapper.toDto( ticket )
                : ticketMapper.toDtoWithReplies( ticket, replies );
    }

    @Transactional( readOnly = true )
    public Page<TicketResponseDto> findAllPaged( TicketFilterDto filter, Pageable pageable )
    {
        Specification<Ticket> specification = ticketSpecificationBuilder.build( filter );

        Page<Ticket> ticketPaged = ticketRepository.findAll( specification, pageable );

        return ticketPaged.map( ticketMapper::toDto );
    }

    @Transactional
    public TicketResponseDto updateTicket(
            Long id,
            @Valid TicketUpdateDto ticketUpdateDto
    )
    {
        Ticket ticket = ticketRepository
                .findById( id )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Ticket não encontrado. {id}: " + id
                        )
                );

        Category category = null;

        if ( ticketUpdateDto.category() != null )
        {
            category = categoryRepository.findById( ticketUpdateDto.category() )
                    .orElseThrow( () ->
                            new ObjectNotFoundException(
                                    "Categoria não encontrada. {id}: " + ticketUpdateDto.category()
                            )
                    );
        }

        ticketMapper.updateTicketFromDto( ticketUpdateDto, ticket, category );

        if ( category != null )
        {
            ticket.setCategory( category );
        }

        Ticket updatedTicket = ticketRepository.save( ticket );

        return ticketMapper.toDto( updatedTicket );
    }

    @Transactional
    public TicketResponseDto updateTicketStatus( Long id, @Valid TicketStatusUpdateDto ticketStatusUpdateDto )
    {
        Ticket ticket = ticketRepository
                .findById( id )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Ticket não encontrado. {id}: " + id
                        )
                );

        ticketMapper.updateTicketStatusFromDto( ticketStatusUpdateDto, ticket );
        Ticket updatedTicketStatus = ticketRepository.save( ticket );

        return ticketMapper.toDto( updatedTicketStatus );
    }

    @Transactional
    public void deleteTicket( Long id )
    {
        Ticket ticket = ticketRepository
                .findById( id )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Ticket não encontrado. {id}: " + id
                        )
                );

        try
        {
            ticketRepository.deleteById( ticket.getId() );
        }
        catch ( DataIntegrityViolationException ex )
        {
            throw new DataBindingViolationException(
                    "Não é possível excluir pois há entidades relacionadas."
            );
        }
    }
}
