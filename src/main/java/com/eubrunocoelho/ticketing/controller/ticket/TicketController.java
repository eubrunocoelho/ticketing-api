package com.eubrunocoelho.ticketing.controller.ticket;

import com.eubrunocoelho.ticketing.controller.BaseController;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketUpdateDto;
import com.eubrunocoelho.ticketing.filter.ticket.TicketFilter;
import com.eubrunocoelho.ticketing.repository.CategoryRepository;
import com.eubrunocoelho.ticketing.service.ticket.TicketService;
import com.eubrunocoelho.ticketing.sort.ticket.TicketSort;
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
public class TicketController extends BaseController {

    private final TicketService ticketService;
    private final CategoryRepository categoryRepository;

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
            HttpServletRequest request,
            Pageable pageable
    ) {
        TicketFilter filter = new TicketFilter(request, categoryRepository);

        Sort sort = TicketSort.getSort(request.getParameter("sort"));

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
