package com.eubrunocoelho.ticketing.controller.reply;

import com.eubrunocoelho.ticketing.controller.BaseController;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyFilterDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyResponseDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyUpdateDto;
import com.eubrunocoelho.ticketing.service.reply.ReplyService;
import com.eubrunocoelho.ticketing.util.PageableFactory;
import com.eubrunocoelho.ticketing.util.sort.reply.ReplySortResolver;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class ReplyController extends BaseController {

    private final ReplyService replyService;
    private final ReplySortResolver replySortResolver;

    @PostMapping(
            value = "/{ticketId}/reply",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<ReplyResponseDto>> createReply(
            @PathVariable Long ticketId,
            @RequestBody @Valid ReplyCreateDto replyCreateDto
    ) {
        ReplyResponseDto createdReplyResponse = replyService.createReply(
                ticketId,
                replyCreateDto
        );

        return createdResponse(createdReplyResponse, createdReplyResponse.id());
    }

    @GetMapping(
            value = "/{ticketId}/reply/{replyId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<ReplyResponseDto>> findReply(
            @PathVariable Long ticketId,
            @PathVariable Long replyId
    ) {
        ReplyResponseDto replyResponse = replyService.findByTicketIdAndReplyId(
                ticketId,
                replyId
        );

        return okResponse(replyResponse);
    }

    @GetMapping(
            value = "/{ticketId}/reply",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<List<ReplyResponseDto>>> findAllReplyByTicket(
            @PathVariable Long ticketId,
            ReplyFilterDto filter,
            Pageable pageable,
            @RequestParam(name = "sort", required = false) String sortParam
    ) {
        Sort sort = replySortResolver.resolve(sortParam);
        Pageable sortedPageable = PageableFactory.build(pageable, sort);

        Page<ReplyResponseDto> pageableRepliesResponse = replyService.findAllByTicketIdPaged(ticketId, filter, sortedPageable);

        return okResponse(pageableRepliesResponse);
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
        ReplyResponseDto updatedReplyResponse = replyService.updateReply(
                ticketId,
                replyId,
                replyUpdateDto
        );

        return okResponse(updatedReplyResponse);
    }

    @DeleteMapping(
            value = "/{ticketId}/reply/{replyId}"
    )
    public ResponseEntity<Void> deleteReply(
            @PathVariable Long ticketId,
            @PathVariable Long replyId
    ) {
        replyService.deleteReply(ticketId, replyId);

        return noContentResponse();
    }
}
