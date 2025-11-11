package com.eubrunocoelho.ticketing.security.authorization;

import com.eubrunocoelho.ticketing.repository.TicketRepository;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import org.springframework.stereotype.Component;

@Component( "ticketSecurity" )
public class TicketSecurity extends BaseSecurity
{
    private final TicketRepository ticketRepository;

    public TicketSecurity(
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
