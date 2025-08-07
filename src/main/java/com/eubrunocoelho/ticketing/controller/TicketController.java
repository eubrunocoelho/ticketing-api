package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.service.ticket.TicketService;
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
public class TicketController extends AbstractController {

    private final TicketService ticketService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<TicketResponseDto>> createTicket(@RequestBody @Valid TicketCreateDto ticketCreateDto) {
        TicketResponseDto ticketResponseDto = ticketService.createTicket(ticketCreateDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                        ticketResponseDto.id()
                )
                .toUri();

        ResponseDto<TicketResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                ticketResponseDto
        );

        return ResponseEntity.created(location).body(responseDto);
    }
}
