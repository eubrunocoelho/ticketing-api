package com.eubrunocoelho.ticketing.controller.ticket;

import com.eubrunocoelho.ticketing.controller.AbstractController;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketUpdateDto;
import com.eubrunocoelho.ticketing.service.ticket.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<TicketResponseDto>> updateTicket(
            @PathVariable Long id,
            @RequestBody @Valid TicketUpdateDto ticketUpdateDto
    ) {
        TicketResponseDto ticketResponseDto = ticketService.updateTicket(id, ticketUpdateDto);

        ResponseDto<TicketResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                ticketResponseDto
        );

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<List<TicketResponseDto>>> findAll() {
        List<TicketResponseDto> listTicketResponseDto = ticketService.findAll();

        ResponseDto<List<TicketResponseDto>> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                listTicketResponseDto
        );

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<TicketResponseDto>> findTicket(
            @PathVariable Long id
    ) {
        TicketResponseDto ticketResponseDto = ticketService.findById(id);

        ResponseDto<TicketResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                ticketResponseDto
        );

        return ResponseEntity.ok().body(responseDto);
    }
}
