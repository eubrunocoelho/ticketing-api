package com.eubrunocoelho.ticketing.service.reply;

import com.eubrunocoelho.ticketing.dto.reply.ReplyFilterDto;
import com.eubrunocoelho.ticketing.event.reply.ReplyCreatedEvent;
import com.eubrunocoelho.ticketing.exception.entity.DataBindingViolationException;
import com.eubrunocoelho.ticketing.repository.specification.ReplySpecificationBuilder;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyResponseDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyUpdateDto;
import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.entity.ObjectNotFoundException;
import com.eubrunocoelho.ticketing.mapper.ReplyMapper;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import com.eubrunocoelho.ticketing.repository.TicketRepository;
import com.eubrunocoelho.ticketing.service.reply.validation.ReplyValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService
{
    private final ApplicationEventPublisher eventPublisher;
    private final TicketRepository ticketRepository;
    private final UserPrincipalService userPrincipalService;
    private final ReplyRepository replyRepository;
    private final ReplyFactory replyFactory;
    private final ReplyValidationService replyValidationService;
    private final ReplyMapper replyMapper;
    private final ReplySpecificationBuilder replySpecificationBuilder;

    @Transactional
    public ReplyResponseDto createReply(
            Long ticketId,
            ReplyCreateDto dto
    )
    {
        User loggedUser = userPrincipalService.getLoggedInUser();
        Ticket ticket = ticketRepository
                .findById( ticketId )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Ticket não encontrado. {id}: " + ticketId
                        )
                );

        Reply reply = replyFactory.buildReply( ticketId, dto, loggedUser, ticket );
        replyValidationService.validate( ticketId, reply, loggedUser );

        Reply createdReply = replyRepository.save( reply );

        eventPublisher.publishEvent( new ReplyCreatedEvent( this, createdReply ) );

        return replyMapper.toDto( createdReply );
    }

    @Transactional( readOnly = true )
    public ReplyResponseDto findByTicketIdAndReplyId( Long ticketId, Long replyId )
    {
        Reply reply = replyRepository
                .findByTicketIdAndId( ticketId, replyId )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Resposta não encontrada. {ticketId}: "
                                        + ticketId
                                        + ", {replyId}: "
                                        + replyId
                        )
                );

        return replyMapper.toDto( reply );
    }

    @Transactional( readOnly = true )
    public Page<ReplyResponseDto> findAllByTicketIdPaged(
            Long ticketId,
            ReplyFilterDto filter,
            Pageable pageable
    )
    {
        Ticket ticket = ticketRepository
                .findById( ticketId )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Ticket não encontrado. {id}: " + ticketId
                        )
                );

        Specification<Reply> ticketSpecification =
                ( root, query, cb ) -> cb.equal(
                        root.get( "ticket" ).get( "id" ),
                        ticket.getId()
                );

        Specification<Reply> filterSpecification = replySpecificationBuilder.build( filter );
        Specification<Reply> finalSpecification = ticketSpecification.and( filterSpecification );

        return replyRepository.findAll( finalSpecification, pageable )
                .map( replyMapper::toDto );
    }

    @Transactional
    public ReplyResponseDto updateReply(
            Long ticketId,
            Long replyId,
            ReplyUpdateDto replyUpdateDto
    )
    {
        Reply reply = replyRepository
                .findByTicketIdAndId( ticketId, replyId )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Resposta não encontrada. {ticketId}: "
                                        + ticketId
                                        + ", {replyId}: "
                                        + replyId
                        )
                );

        replyMapper.updateReplyFromDto( replyUpdateDto, reply );
        Reply updatedReply = replyRepository.save( reply );

        return replyMapper.toDto( updatedReply );
    }

    @Transactional
    public void deleteReply( Long ticketId, Long replyId )
    {
        Reply reply = replyRepository
                .findByTicketIdAndId( ticketId, replyId )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                            "Resposta não encontrada. {ticketId}: "
                                    + ticketId
                                    + ", {replyId}: "
                                    + replyId
                        )
                );

        try
        {
            replyRepository.deleteById( reply.getId() );
        }
        catch ( DataIntegrityViolationException ex )
        {
            throw new DataBindingViolationException(
                    "Não é possível excluir pois há entidades relacionadas."
            );
        }
    }
}
