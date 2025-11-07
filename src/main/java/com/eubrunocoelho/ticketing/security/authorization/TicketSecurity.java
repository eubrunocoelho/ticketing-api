package com.eubrunocoelho.ticketing.security.authorization;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.repository.TicketRepository;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component( "ticketSecurity" )
@RequiredArgsConstructor
public class TicketSecurity
{
    private final UserPrincipalService userPrincipalService;
    private final TicketRepository ticketRepository;

    public boolean canCreateTicket()
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        return !(
                loggedUser.getRole() == User.Role.ROLE_ADMIN
                        || loggedUser.getRole() == User.Role.ROLE_STAFF
        );
    }

    public boolean canAccessTicket( Long ticketId )
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

    public boolean canAccessAllTickets()
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        return loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF;
    }

    public boolean canUpdateTicket( Long ticketId )
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

    public boolean canDeleteTicket( Long ticketId )
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
}
