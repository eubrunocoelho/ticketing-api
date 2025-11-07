package com.eubrunocoelho.ticketing.service.reply.validation;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.business.SelfReplyNotAllowedException;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ReplyValidationService
{
    private final ReplyRepository replyRepository;

    public void validate( Long ticketId, Reply reply, User loggedUser )
    {
        boolean existsReply = replyRepository.existsByTicketId( ticketId );


        if ( !existsReply && reply.getTicket().getUser().getId().equals( loggedUser.getId() ) )
        {
            throw new SelfReplyNotAllowedException(
                    "Você não pode responder diretamente seu próprio ticket ou sua própria resposta."
            );
        }

        Optional<Reply> lastReply = replyRepository.findTopByTicketIdOrderByCreatedAtDesc( ticketId );

        if ( lastReply.isPresent() )
        {
            if ( lastReply.get().getCreatedUser().getId().equals( loggedUser.getId() ) )
            {
                throw new SelfReplyNotAllowedException(
                        "Você não pode responder diretamente seu próprio ticket ou sua própria resposta."
                );
            }
        }

        return;
    }
}
