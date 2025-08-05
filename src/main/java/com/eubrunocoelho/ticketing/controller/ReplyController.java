package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class ReplyController extends AbstractController {

    private final ReplyService replyService;

    @PostMapping(
            value = "/{ticketId}/replies",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> createTicketReply(
            @PathVariable Long ticketId,
            @RequestBody @Valid ReplyCreateDto replyCreateDto
    ) {
        replyService.createReply(ticketId, replyCreateDto);

        return ResponseEntity.ok(null);

    }
}
