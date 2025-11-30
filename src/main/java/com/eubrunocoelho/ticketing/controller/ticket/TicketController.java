package com.eubrunocoelho.ticketing.controller.ticket;

import com.eubrunocoelho.ticketing.controller.BaseController;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketByUserFilterDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketFilterDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketStatusUpdateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketUpdateDto;
import com.eubrunocoelho.ticketing.service.ticket.TicketService;
import com.eubrunocoelho.ticketing.util.PageableFactory;
import com.eubrunocoelho.ticketing.util.ApiResponseBuilder;
import com.eubrunocoelho.ticketing.util.sort.ticket.TicketSortResolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin( "*" )
@RestController
@RequestMapping( "/tickets" )
@Tag( name = "Gerenciador de tickets." )
public class TicketController extends BaseController
{
    private final TicketService ticketService;
    private final TicketSortResolver ticketSortResolver;

    public TicketController(
            TicketService ticketService,
            TicketSortResolver ticketSortResolver,
            ApiResponseBuilder apiResponseBuilder
    )
    {
        super( apiResponseBuilder );

        this.ticketService = ticketService;
        this.ticketSortResolver = ticketSortResolver;
    }


    @Operation(
            summary = "Cadastrar ticket.",
            description = "Responsável por cadastrar um ticket."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Ticket cadastrado.",
                            content = @Content( schema = @Schema( implementation = TicketResponseDto.class ) )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Erro de validação.",
                            content = @Content( schema = @Schema() )
                    )
            }
    )
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@ticketPermission.canCreateTicket()" )
    public ResponseEntity<ResponseDto<TicketResponseDto>> createTicket(
            @RequestBody TicketCreateDto ticketCreateDto
    )
    {
        TicketResponseDto createdTicketResponse = ticketService.createTicket( ticketCreateDto );

        return createdResponse( createdTicketResponse, createdTicketResponse.id() );
    }

    @Operation(
            summary = "Encontrar ticket.",
            description = "Responsável por encontrar um determinado ticket."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dados do ticket.",
                            content = @Content( schema = @Schema( implementation = TicketResponseDto.class ) )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ticket não encontrado.",
                            content = @Content( schema = @Schema() )
                    )
            }
    )
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@ticketPermission.canAccessTicket(#id)" )
    public ResponseEntity<ResponseDto<TicketResponseDto>> findTicket(
            @PathVariable Long id
    )
    {
        TicketResponseDto ticketResponse = ticketService.findById( id );

        return okResponse( ticketResponse );
    }

    @Operation(
            summary = "Encontrar todos os tickets.",
            description = "Responsável por encontrar e listar todos os tickets."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de tickets.",
                            content = @Content( schema = @Schema( implementation = TicketResponseDto.class ) )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    )
            }
    )
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@ticketPermission.canAccessAllTickets()" )
    public ResponseEntity<ResponseDto<List<TicketResponseDto>>> findAllTickets(
            TicketFilterDto filter,
            Pageable pageable,
            @RequestParam( name = "sort", required = false ) String sortParam
    )
    {
        Sort sort = ticketSortResolver.resolve( sortParam );
        Pageable sortedPageable = PageableFactory.build( pageable, sort );

        Page<TicketResponseDto> pageableTicketsResponse = ticketService.findAllPaged(
                filter,
                sortedPageable
        );

        return okResponse( pageableTicketsResponse );
    }

    @Operation(
            summary = "Encontrar todos os tickets de um usuário específico.",
            description = "Responsável por encontrar e listar todos os tickets de um usuário específico."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de tickets.",
                            content = @Content( schema = @Schema( implementation = TicketResponseDto.class ) )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    )
            }
    )
    @GetMapping(
            value = "/user/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@ticketPermission.canAccessAllTicketsByUser(#userId)" )
    public ResponseEntity<ResponseDto<List<TicketResponseDto>>> findAllTicketsByUser(
            @PathVariable Long userId,
            TicketByUserFilterDto filter,
            Pageable pageable,
            @RequestParam( name = "sort", required = false ) String sortParam
    )
    {
        Sort sort = ticketSortResolver.resolve( sortParam );
        Pageable sortedPageable = PageableFactory.build( pageable, sort );

        Page<TicketResponseDto> pageableTicketsResponse = ticketService.findAllByUserIdPaged(
                userId, filter, sortedPageable
        );

        return okResponse( pageableTicketsResponse );
    }

    @Operation(
            summary = "Atualizar ticket.",
            description = "Responsável por atualizar dados de um determinado ticket."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ticket atualizado.",
                            content = @Content( schema = @Schema( implementation = TicketResponseDto.class ) )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ticket não encontrado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Erro de validação.",
                            content = @Content( schema = @Schema() )
                    )
            }
    )
    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@ticketPermission.canUpdateTicket(#id)" )
    public ResponseEntity<ResponseDto<TicketResponseDto>> updateTicket(
            @PathVariable Long id,
            @RequestBody TicketUpdateDto ticketUpdateDto
    )
    {
        TicketResponseDto updatedTicketResponse = ticketService.updateTicket( id, ticketUpdateDto );

        return okResponse( updatedTicketResponse );
    }

    @Operation(
            summary = "Atualizar status do ticket.",
            description = "Responsável por atualizar o status de um determinado ticket."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Status do ticket atualizado.",
                            content = @Content( schema = @Schema( implementation = TicketResponseDto.class ) )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ticket não encontrado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Erro de validação.",
                            content = @Content( schema = @Schema() )
                    )
            }
    )
    @PatchMapping(
            value = "/{id}/status",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@ticketPermission.canUpdateTicketStatus(#id)" )
    public ResponseEntity<ResponseDto<TicketResponseDto>> updateTicketStatus(
            @PathVariable Long id,
            @RequestBody TicketStatusUpdateDto ticketStatusUpdateDto
    )
    {
        TicketResponseDto updatedTicketStatusResponse = ticketService.updateTicketStatus( id, ticketStatusUpdateDto );

        return okResponse( updatedTicketStatusResponse );
    }

    @Operation(
            summary = "Deletar ticket.",
            description = "Responsável por deletar determinado ticket."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Ticket deletado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ticket não encontrado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflito no banco de dados.",
                            content = @Content( schema = @Schema() )
                    )
            }
    )
    @DeleteMapping(
            value = "/{id}"
    )
    @PreAuthorize( "@ticketPermission.canDeleteTicket(#id)" )
    public ResponseEntity<Void> deleteTicket(
            @PathVariable Long id
    )
    {
        ticketService.deleteTicket( id );

        return noContentResponse();
    }
}
