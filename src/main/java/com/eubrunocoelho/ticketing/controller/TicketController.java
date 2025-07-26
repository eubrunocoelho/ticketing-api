package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.tickets.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.tickets.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.Tickets;
import com.eubrunocoelho.ticketing.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TicketResponseDto> createTicket(@RequestBody @Valid TicketCreateDto ticketCreateDto) {
        Tickets ticket = ticketService.createTicket(ticketCreateDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                        ticket.getId()
                )
                .toUri();

        UserResponseDto userResponseDto = new UserResponseDto(
                "",
                ticket.getUser().getId(),
                ticket.getUser().getUsername(),
                ticket.getUser().getEmail()
        );

        TicketResponseDto responseDto = new TicketResponseDto(
                ticket.getId(),
                userResponseDto,
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getCategory()
        );

        return ResponseEntity.created(location).body(responseDto);
    }
}
