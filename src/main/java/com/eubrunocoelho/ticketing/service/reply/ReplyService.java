package com.eubrunocoelho.ticketing.service.reply;

import com.eubrunocoelho.ticketing.authentication.LoginUtilityService;
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
import com.eubrunocoelho.ticketing.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final TicketRepository ticketRepository;
    private final LoginUtilityService loginUtilityService;
    private final ReplyRepository replyRepository;
    private final ReplyFactory replyFactory;
    private final ReplyValidationService replyValidationService;
    private final ReplyMapper replyMapper;

    public ReplyResponseDto createReply(Long ticketId, ReplyCreateDto dto) {
        User loggedUser = loginUtilityService.getLoggedInUser();
        Ticket ticket = ticketRepository
                .findById(ticketId)
                .orElseThrow(
                        () ->
                                new ObjectNotFoundException(
                                        "Ticket não encontrado. {id}: " + ticketId
                                )
                );

        Reply reply = replyFactory.buildReply(ticketId, dto, loggedUser, ticket);
        replyValidationService.validate(reply, loggedUser);

        Reply createdReply = replyRepository.save(reply);

        return replyMapper.toDto(createdReply);
    }

    public ReplyResponseDto updateReply(
            Long ticketId,
            Long replyId,
            ReplyUpdateDto replyUpdateDto
    ) {
        Reply reply = replyRepository
                .findByTicketIdAndId(ticketId, replyId)
                .orElseThrow(
                        () ->
                                new ObjectNotFoundException(
                                        "Resposta não encontrada. {ticketId}: "
                                                + ticketId
                                                + ", {replyId}: "
                                                + replyId
                                )
                );

        replyMapper.updateReplyFromDto(replyUpdateDto, reply);
        Reply updatedReply = replyRepository.save(reply);

        return replyMapper.toDto(updatedReply);
    }

    public ReplyResponseDto findByTicketIdAndReplyId(Long ticketId, Long replyId) {
        Reply reply = replyRepository
                .findByTicketIdAndId(ticketId, replyId)
                .orElseThrow(
                        () ->
                                new ObjectNotFoundException(
                                        "Resposta não encontrada. {ticketId}: "
                                                + ticketId
                                                + ", {replyId}: "
                                                + replyId
                                )
                );

        return replyMapper.toDto(reply);
    }
}
