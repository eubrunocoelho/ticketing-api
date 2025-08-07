package com.eubrunocoelho.ticketing.service.reply;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubsequentReplyStrategy implements ReplyStrategy {

    private final ReplyRepository replyRepository;

    @Override
    public boolean applies(Long ticketId) {
        return replyRepository.existsByTicketId(ticketId);
    }

    @Override
    public void configure(Reply reply, Long ticketId, Ticket ticket) {
        Reply lastReply = replyRepository.findTopByTicketIdOrderByCreatedAtDesc(ticketId)
                .orElseThrow(() -> new IllegalStateException("Not found"));
        reply.setParent(lastReply);
        reply.setRespondedToUser(lastReply.getCreatedUser());
    }
}
