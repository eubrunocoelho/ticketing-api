package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.authentication.LoginUtilityService;
import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final TicketService ticketService;
    private final LoginUtilityService loginUtilityService;

    public void createReply(Long id, ReplyCreateDto replyCreateDto) {
        User loggedUser = loginUtilityService.getLoggedInUser();
        Ticket ticket = ticketService.findById(id);

        Reply reply = new Reply();

        if (!hasReplies(ticket.getId())) {
            reply.setTicket(ticket);
            reply.setCreatedUser(ticket.getUser());
            reply.setRespondedToUser(loggedUser);
            reply.setParent(null);
            reply.setContent(replyCreateDto.content());

            Reply createdReply = replyRepository.save(reply);
        }
    }

    public boolean hasReplies(Long ticketId) {
        return replyRepository.existsByTicketId(ticketId);
    }
}
