package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.response.ResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.reply.TicketReplyCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.reply.TicketReplyResponseDto;
import com.eubrunocoelho.ticketing.service.TicketReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
public class TicketReplyController extends AbstractController {

    private final TicketReplyService ticketReplyService;

    @PostMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<TicketReplyResponseDto>> createTicketReply(@PathVariable Long id, @RequestBody @Valid TicketReplyCreateDto ticketReplyCreateDto) {
        TicketReplyResponseDto ticketReplyResponseDto = ticketReplyService.createTicketReply(id, ticketReplyCreateDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                        ticketReplyResponseDto.id()
                )
                .toUri();

        ResponseDto<TicketReplyResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                ticketReplyResponseDto
        );

        return ResponseEntity.created(location).body(responseDto);

    }
}
