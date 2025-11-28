package com.eubrunocoelho.ticketing.security.permission;

import com.eubrunocoelho.ticketing.repository.TicketRepository;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import org.springframework.stereotype.Component;

@Component( "ticketPermission" )
public class TicketPermission extends BasePermission
{
    private final TicketRepository ticketRepository;

    public TicketPermission(
            UserPrincipalService userPrincipalService,
            TicketRepository ticketRepository
    )
    {
        super( userPrincipalService );

        this.ticketRepository = ticketRepository;
    }

    public boolean canCreateTicket()
    {
        return !isAdminOrStaff( getLoggedUser() );
    }

    public boolean canAccessTicket( Long ticketId )
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

    public boolean canAccessAllTickets()
    {
        return isAdminOrStaff( getLoggedUser() );
    }

    public boolean canAccessAllTicketsByUser( Long userId )
    {
        if ( isAdminOrStaff( getLoggedUser() ) )
        {
            return true;
        }

        return isOwner( getLoggedUser(), userId );
    }

    public boolean canUpdateTicket( Long ticketId )
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

    public boolean canUpdateTicketStatus( Long ticketId )
    {
        return isAdminOrStaff( getLoggedUser() );
    }

    public boolean canDeleteTicket( Long ticketId )
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
}
