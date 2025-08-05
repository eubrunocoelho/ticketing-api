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

    public void createReply(Long ticketId, ReplyCreateDto replyCreateDto) {
        User loggedUser = loginUtilityService.getLoggedInUser();
        Ticket ticket = ticketService.findById(ticketId);

        Reply reply = new Reply();
        reply.setTicket(ticket);
        reply.setContent(replyCreateDto.content());
        reply.setCreatedUser(loggedUser);

        if (!hasReplies(ticket.getId())) {
            if (loggedUser.getId().equals(ticket.getUser().getId())) {
                throw new IllegalArgumentException("loggedUser == respondedToUser");
            }

            reply.setParent(null);
            reply.setRespondedToUser(ticket.getUser());
        } else {
            Reply lastReply = replyRepository.findTopByTicketIdOrderByCreatedAtDesc(ticketId)
                    .orElseThrow(() -> new IllegalStateException("Not found."));

            User lastReplyAuthor = lastReply.getCreatedUser();

            if (loggedUser.getId().equals(lastReplyAuthor.getId())) {
                throw new IllegalArgumentException("loggedUser == respondedToUser");
            }

            reply.setParent(lastReply);
            reply.setRespondedToUser(lastReplyAuthor);
        }

        replyRepository.save(reply);
    }

    public boolean hasReplies(Long ticketId) {
        return replyRepository.existsByTicketId(ticketId);
    }
}
