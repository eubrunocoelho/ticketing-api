package com.eubrunocoelho.ticketing.service.reply;

import com.eubrunocoelho.ticketing.authentication.LoginUtilityService;
import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
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

    public void createReply(Long ticketId, ReplyCreateDto dto) {
        User loggedUser = loginUtilityService.getLoggedInUser();
        Ticket ticket = ticketService.findById(ticketId);

        Reply reply = replyFactory.buildReply(ticketId, dto, loggedUser, ticket);

        replyValidationService.validate(reply, loggedUser);

        replyRepository.save(reply);
    }
}
