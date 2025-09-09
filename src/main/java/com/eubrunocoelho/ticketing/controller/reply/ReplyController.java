package com.eubrunocoelho.ticketing.controller.reply;

import com.eubrunocoelho.ticketing.controller.BaseController;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyResponseDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyUpdateDto;
import com.eubrunocoelho.ticketing.filter.reply.ReplyFilter;
import com.eubrunocoelho.ticketing.service.reply.ReplyService;
import com.eubrunocoelho.ticketing.sort.reply.ReplySort;
import com.eubrunocoelho.ticketing.util.PageableFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class ReplyController extends BaseController {

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
            HttpServletRequest request,
            Pageable pageable
    ) {
        ReplyFilter filter = new ReplyFilter(request);

        Sort sort = ReplySort.getSort(request.getParameter("sort"));

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
