package com.eubrunocoelho.ticketing.controller.ticket;

import com.eubrunocoelho.ticketing.controller.BaseController;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketFilterDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketUpdateDto;
import com.eubrunocoelho.ticketing.service.ticket.TicketService;
import com.eubrunocoelho.ticketing.util.PageableFactory;
import com.eubrunocoelho.ticketing.util.ResponseBuilder;
import com.eubrunocoelho.ticketing.util.sort.ticket.TicketSortResolver;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/tickets" )
public class TicketController extends BaseController
{
    private final TicketService ticketService;
    private final TicketSortResolver ticketSortResolver;

    public TicketController(
            TicketService ticketService,
            TicketSortResolver ticketSortResolver,
            ResponseBuilder responseBuilder
    )
    {
        super( responseBuilder );

        this.ticketService = ticketService;
        this.ticketSortResolver = ticketSortResolver;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<TicketResponseDto>> createTicket(
            @RequestBody @Valid TicketCreateDto ticketCreateDto
    )
    {
        TicketResponseDto createdTicketResponse = ticketService.createTicket( ticketCreateDto );

        return createdResponse( createdTicketResponse, createdTicketResponse.id() );
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@ticketSecurity.canAccessTicket(#id)" )
    public ResponseEntity<ResponseDto<TicketResponseDto>> findTicket(
            @PathVariable Long id
    )
    {
        TicketResponseDto ticketResponse = ticketService.findById( id );

        return okResponse( ticketResponse );
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@ticketSecurity.canAccessAllTickets()" )
    public ResponseEntity<ResponseDto<List<TicketResponseDto>>> findAll(
            TicketFilterDto filter,
            Pageable pageable,
            @RequestParam( name = "sort", required = false ) String sortParam
    )
    {
        Sort sort = ticketSortResolver.resolve( sortParam );
        Pageable sortedPageable = PageableFactory.build( pageable, sort );

        Page<TicketResponseDto> pageableTicketsResponse = ticketService.findAllPaged(
                filter,
                sortedPageable
        );

        return okResponse( pageableTicketsResponse );
    }

    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@ticketSecurity.canUpdateTicket(#id)" )
    public ResponseEntity<ResponseDto<TicketResponseDto>> updateTicket(
            @PathVariable Long id,
            @RequestBody @Valid TicketUpdateDto ticketUpdateDto
    )
    {
        TicketResponseDto updatedTicketResponse = ticketService.updateTicket( id, ticketUpdateDto );

        return okResponse( updatedTicketResponse );
    }

    @DeleteMapping(
            value = "/{id}"
    )
    @PreAuthorize( "@ticketSecurity.canDeleteTicket(#id)" )
    public ResponseEntity<Void> deleteTicket(
            @PathVariable Long id
    )
    {
        ticketService.deleteTicket( id );

        return noContentResponse();
    }
}
