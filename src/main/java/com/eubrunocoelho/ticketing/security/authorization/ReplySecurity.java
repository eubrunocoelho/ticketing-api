package com.eubrunocoelho.ticketing.security.authorization;

import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import com.eubrunocoelho.ticketing.repository.TicketRepository;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import org.springframework.stereotype.Component;

@Component( "replySecurity" )
public class ReplySecurity extends BaseSecurity
{
    private final TicketRepository ticketRepository;
    private final ReplyRepository replyRepository;

    public ReplySecurity(
            UserPrincipalService userPrincipalService,
            TicketRepository ticketRepository,
            ReplyRepository replyRepository
    )
    {
        super( userPrincipalService );

        this.ticketRepository = ticketRepository;
        this.replyRepository = replyRepository;
    }

    public boolean canCreateReply( Long ticketId )
    {
        if ( isAdminOrStaff( getLoggedUser() ) )
        {
            return true;
        }

        return ticketRepository
                .findById( ticketId )
                .map(
                        ticket -> isOwner( getLoggedUser(), ticket.getUser().getId() )
                )
                .orElse( false );
    }

    public boolean canAccessReply( Long ticketId )
    {
        if ( isAdminOrStaff( getLoggedUser() ) )
        {
            return true;
        }

        return ticketRepository
                .findById( ticketId )
                .map(
                        ticket -> isOwner( getLoggedUser(), ticket.getUser().getId() )
                )
                .orElse( false );
    }

    public boolean canAccessRepliesByTicket( Long ticketId )
    {
        if ( isAdminOrStaff( getLoggedUser() ) )
        {
            return true;
        }

        return ticketRepository
                .findById( ticketId )
                .map(
                        ticket -> isOwner( getLoggedUser(), ticket.getUser().getId() )
                )
                .orElse( false );
    }

    public boolean canUpdateReply( Long ticketId, Long replyId )
    {
        if ( isAdminOrStaff( getLoggedUser() ) )
        {
            return true;
        }

        return replyRepository
                .findById( replyId )
                .map(
                        reply ->
                        {
                            boolean isTicketOwner = ticketRepository
                                    .findById( ticketId )
                                    .map( ticket -> isOwner( getLoggedUser(), ticket.getUser().getId() ) )
                                    .orElse( false );

                            boolean isReplyOwner = isOwner( getLoggedUser(), reply.getCreatedUser().getId() );

                            return isTicketOwner && isReplyOwner;
                        }
                )
                .orElse( false );
    }

    public boolean canDeleteReply( Long ticketId, Long replyId )
    {
        if ( isAdminOrStaff( getLoggedUser() ) )
        {
            return true;
        }

        return replyRepository
                .findById( replyId )
                .map(
                        reply ->
                        {
                            boolean isTicketOwner = ticketRepository
                                    .findById( ticketId )
                                    .map( ticket -> isOwner( getLoggedUser(), ticket.getUser().getId() ) )
                                    .orElse( false );

                            boolean isReplyOwner = isOwner( getLoggedUser(), reply.getCreatedUser().getId() );

                            return isTicketOwner && isReplyOwner;
                        }
                )
                .orElse( false );
    }
}
