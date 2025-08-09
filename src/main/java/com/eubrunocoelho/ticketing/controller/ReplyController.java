package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyResponseDto;
import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.mapper.ReplyMapper;
import com.eubrunocoelho.ticketing.service.reply.ReplyService;
import com.eubrunocoelho.ticketing.service.ticket.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class ReplyController extends AbstractController {

    private final ReplyService replyService;
    private final ReplyMapper replyMapper;

    @PostMapping(
            value = "/{ticketId}/reply",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<ReplyResponseDto>> createTicketReply(
            @PathVariable Long ticketId,
            @RequestBody @Valid ReplyCreateDto replyCreateDto
    ) {
        ReplyResponseDto replyResponseDto = replyService.createReply(ticketId, replyCreateDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{replyId}")
                .buildAndExpand(
                        replyResponseDto.id()
                )
                .toUri();

        ResponseDto<ReplyResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                replyResponseDto
        );

        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping(
            value = "/{ticketId}/reply/{replyId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<ReplyResponseDto>> findById(@PathVariable Long ticketId, @PathVariable Long replyId) {
        Reply reply = replyService.findByTicketIdAndId(ticketId, replyId);
        ReplyResponseDto replyResponseDto = replyMapper.toDto(reply);

        ResponseDto<ReplyResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                replyResponseDto
        );

        return ResponseEntity.ok().body(responseDto);
    }
}
