package com.eubrunocoelho.ticketing.controller.reply;

import com.eubrunocoelho.ticketing.controller.AbstractController;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.meta.MetaResponseDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyResponseDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyUpdateDto;
import com.eubrunocoelho.ticketing.filter.reply.ReplyFilter;
import com.eubrunocoelho.ticketing.service.reply.ReplyService;
import com.eubrunocoelho.ticketing.sort.reply.ReplySort;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
                replyResponseDto,
                null
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
                replyResponseDto,
                null
        );

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping(
            value = "/{ticketId}/reply/{replyId}"
    )
    public ResponseEntity<Void> deleteReply(
            @PathVariable Long ticketId,
            @PathVariable Long replyId
    ) {
        replyService.deleteReply(ticketId, replyId);

        return ResponseEntity.noContent().build();
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

        String sortParam = request.getParameter("sort");
        Sort sort = ReplySort.getSort(sortParam);

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<ReplyResponseDto> page = replyService.findAllByTicketIdPaged(ticketId, filter, sortedPageable);

        MetaResponseDto meta = new MetaResponseDto(
                page.isFirst(),
                page.isLast(),
                page.getNumberOfElements(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages()
        );

        ResponseDto<List<ReplyResponseDto>> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                page.getContent(),
                meta
        );

        return ResponseEntity.ok(responseDto);
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
                replyResponseDto,
                null
        );

        return ResponseEntity.ok().body(responseDto);
    }
}
