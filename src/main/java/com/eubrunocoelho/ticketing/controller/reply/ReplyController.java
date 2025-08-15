package com.eubrunocoelho.ticketing.controller.reply;

import com.eubrunocoelho.ticketing.controller.AbstractController;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyResponseDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyUpdateDto;
import com.eubrunocoelho.ticketing.service.reply.ReplyService;
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
public class ReplyController extends AbstractController {

    private final ReplyService replyService;

    @PostMapping(
            value = "/{ticketId}/reply",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<ReplyResponseDto>> createReply(
            @PathVariable Long ticketId,
            @RequestBody @Valid ReplyCreateDto replyCreateDto
    ) {
        ReplyResponseDto replyResponseDto = replyService.createReply(
                ticketId,
                replyCreateDto
        );

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

    @PatchMapping(
            value = "/{ticketId}/reply/{replyId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<ReplyResponseDto>> updateReply(
            @PathVariable Long ticketId,
            @PathVariable Long replyId,
            @RequestBody ReplyUpdateDto replyUpdateDto
    ) {
        ReplyResponseDto replyResponseDto = replyService.updateReply(
                ticketId,
                replyId,
                replyUpdateDto
        );

        ResponseDto<ReplyResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                replyResponseDto
        );

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(
            value = "/{ticketId}/reply",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<List<ReplyResponseDto>>> findAllReplyByTicket(
            @PathVariable Long ticketId
    ) {
        List<ReplyResponseDto> listReplyResponseDto = replyService.findAllByTicketId(ticketId);

        ResponseDto<List<ReplyResponseDto>> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                listReplyResponseDto
        );

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(
            value = "/{ticketId}/reply/{replyId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<ReplyResponseDto>> findReply(
            @PathVariable Long ticketId,
            @PathVariable Long replyId
    ) {
        ReplyResponseDto replyResponseDto = replyService.findByTicketIdAndReplyId(
                ticketId,
                replyId
        );

        ResponseDto<ReplyResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                replyResponseDto
        );

        return ResponseEntity.ok().body(responseDto);
    }
}
