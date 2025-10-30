package com.eubrunocoelho.ticketing.security.authorization;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import com.eubrunocoelho.ticketing.repository.TicketRepository;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component( "replySecurity" )
@RequiredArgsConstructor
public class ReplySecurity
{
    private final UserPrincipalService userPrincipalService;
    private final TicketRepository ticketRepository;
    private final ReplyRepository replyRepository;

    public boolean canCreateReply( Long ticketId )
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        if ( loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF
        )
        {
            return true;
        }

        return ticketRepository
                .findById( ticketId )
                .map(
                        ticket -> ticket.getUser().getId().equals( loggedUser.getId() )
                )
                .orElse( false );
    }

    public boolean canAccessReply( Long ticketId )
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        if ( loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF
        )
        {
            return true;
        }

        return ticketRepository
                .findById( ticketId )
                .map(
                        ticket -> ticket.getUser().getId().equals( loggedUser.getId() )
                )
                .orElse( false );
    }

    public boolean canAccessRepliesByTicket( Long ticketId )
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        if ( loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF
        )
        {
            return true;
        }

        return ticketRepository
                .findById( ticketId )
                .map(
                        ticket -> ticket.getUser().getId().equals( loggedUser.getId() )
                )
                .orElse( false );
    }

    public boolean canUpdateReply( Long ticketId, Long replyId )
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        if ( loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF
        )
        {
            return true;
        }

        Reply reply = replyRepository
                .findByTicketIdAndId( ticketId, replyId )
                .orElse( null );

        if ( reply == null )
        {
            return false;
        }

        Ticket ticket = reply.getTicket();

        boolean isTicketOwner = ticket.getUser().getId().equals( loggedUser.getId() );
        boolean isReplyOwner = reply.getCreatedUser().getId().equals( loggedUser.getId() );

        return isTicketOwner && isReplyOwner;
    }

    public boolean canDeleteReply( Long ticketId, Long replyId )
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        if ( loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF
        )
        {
            return true;
        }

        Reply reply = replyRepository
                .findByTicketIdAndId( ticketId, replyId )
                .orElse( null );

        if ( reply == null )
        {
            return false;
        }

        Ticket ticket = reply.getTicket();

        boolean isTicketOwner = ticket.getUser().getId().equals( loggedUser.getId() );
        boolean isReplyOwner = reply.getCreatedUser().getId().equals( loggedUser.getId() );

        return isTicketOwner && isReplyOwner;
    }
}
