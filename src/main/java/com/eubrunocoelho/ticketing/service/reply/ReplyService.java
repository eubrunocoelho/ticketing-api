package com.eubrunocoelho.ticketing.service.reply;

import com.eubrunocoelho.ticketing.authentication.LoginUtilityService;
import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyResponseDto;
import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.entity.ObjectNotFoundException;
import com.eubrunocoelho.ticketing.mapper.ReplyMapper;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import com.eubrunocoelho.ticketing.service.reply.validation.ReplyValidationService;
import com.eubrunocoelho.ticketing.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final TicketService ticketService;
    private final LoginUtilityService loginUtilityService;
    private final ReplyRepository replyRepository;
    private final ReplyFactory replyFactory;
    private final ReplyValidationService replyValidationService;
    private final ReplyMapper replyMapper;

    public ReplyResponseDto createReply(Long ticketId, ReplyCreateDto dto) {
        User loggedUser = loginUtilityService.getLoggedInUser();
        Ticket ticket = ticketService.findById(ticketId);

        Reply reply = replyFactory.buildReply(ticketId, dto, loggedUser, ticket);
        replyValidationService.validate(reply, loggedUser);

        Reply createdReply = replyRepository.save(reply);

        return replyMapper.toDto(createdReply);
    }

    public Reply findByTicketIdAndId(Long ticketId, Long replyId) {
        return replyRepository.findByTicketIdAndId(ticketId, replyId).orElseThrow(() ->
                new ObjectNotFoundException(
                        "Resposta não encontrada. {ticketId}: "
                                + ticketId
                                + ", {replyId}: "
                                + replyId
                )
        );
    }
}
