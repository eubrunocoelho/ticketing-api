package com.eubrunocoelho.ticketing.controller.ticket;

import com.eubrunocoelho.ticketing.controller.AbstractController;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.meta.MetaResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketUpdateDto;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.filter.ticket.TicketFilter;
import com.eubrunocoelho.ticketing.mapper.TicketMapper;
import com.eubrunocoelho.ticketing.repository.TicketRepository;
import com.eubrunocoelho.ticketing.service.ticket.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class TicketController extends AbstractController {

    private final TicketService ticketService;
    private final TicketRepository ticketRepository;

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
                ticketResponseDto,
                null
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
                ticketResponseDto,
                null
        );

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity<Void> deleteTicket(
            @PathVariable Long id
    ) {
        ticketService.deleteTicket(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<List<TicketResponseDto>>> findAll(HttpServletRequest request, Pageable pageable) {
        TicketFilter filter = new TicketFilter(request);

        Page<Ticket> page = ticketRepository.findAll(filter.toSpecification(), pageable);

        List<TicketResponseDto> listTicketResponseDto = page
                .map(ticketService::toResponseDto)
                .getContent();

        MetaResponseDto meta = new MetaResponseDto(
                page.isFirst(),
                page.isLast(),
                page.getNumberOfElements(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages()
        );

        ResponseDto<List<TicketResponseDto>> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                listTicketResponseDto,
                meta
        );

        return ResponseEntity.ok(responseDto);
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
                ticketResponseDto,
                null
        );

        return ResponseEntity.ok().body(responseDto);
    }
}
