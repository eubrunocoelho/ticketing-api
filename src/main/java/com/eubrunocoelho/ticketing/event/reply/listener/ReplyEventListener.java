package com.eubrunocoelho.ticketing.event.reply.listener;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.event.reply.ReplyCreatedEvent;
import com.eubrunocoelho.ticketing.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyEventListener
{
    private final TicketRepository ticketRepository;

    @EventListener
    public void handleReplyCreated( ReplyCreatedEvent event )
    {
        Reply reply = event.getReply();
        Ticket ticket = reply.getTicket();

        if ( ticket.getStatus() == Ticket.Status.OPEN )
        {
            ticket.setStatus( Ticket.Status.IN_PROGRESS );
            ticketRepository.save( ticket );
        }
    }
}
