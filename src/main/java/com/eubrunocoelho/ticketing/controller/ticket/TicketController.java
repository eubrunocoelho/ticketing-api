package com.eubrunocoelho.ticketing.controller.ticket;

import com.eubrunocoelho.ticketing.controller.BaseController;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketFilterDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketUpdateDto;
import com.eubrunocoelho.ticketing.service.ticket.TicketService;
import com.eubrunocoelho.ticketing.util.sort.TicketSortHelper;
import com.eubrunocoelho.ticketing.util.PageableFactory;
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
public class TicketController extends BaseController {

    private final TicketService ticketService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<TicketResponseDto>> createTicket(
            @RequestBody @Valid TicketCreateDto ticketCreateDto
    ) {
        TicketResponseDto createdTicketResponse = ticketService.createTicket(ticketCreateDto);

        return createdResponse(createdTicketResponse, createdTicketResponse.id());
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<TicketResponseDto>> findTicket(
            @PathVariable Long id
    ) {
        TicketResponseDto ticketResponse = ticketService.findById(id);

        return okResponse(ticketResponse);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<List<TicketResponseDto>>> findAll(
            TicketFilterDto filter,
            Pageable pageable,
            @RequestParam(name = "sort", required = false) String sortParam
    ) {
        Sort sort = TicketSortHelper.getSort(sortParam);
        Pageable sortedPageable = PageableFactory.build(pageable, sort);

        Page<TicketResponseDto> pageableTicketsResponse = ticketService.findAllPaged(filter, sortedPageable);

        return okResponse(pageableTicketsResponse);
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
        TicketResponseDto updatedTicketResponse = ticketService.updateTicket(id, ticketUpdateDto);

        return okResponse(updatedTicketResponse);
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity<Void> deleteTicket(
            @PathVariable Long id
    ) {
        ticketService.deleteTicket(id);

        return noContentResponse();
    }
}
