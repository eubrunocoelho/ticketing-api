package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.authentication.LoginUtilityService;
import com.eubrunocoelho.ticketing.dto.category.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.reply.TicketReplyCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.reply.TicketReplyResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.TicketReply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.repository.TicketReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
/**
 * REFACTOR IN FUTURE
 */
public class TicketReplyService {

    private final TicketReplyRepository ticketReplyRepository;
    private final TicketService ticketService;
    private final LoginUtilityService loginUtilityService;

    public TicketReplyResponseDto createTicketReply(Long id, TicketReplyCreateDto ticketReplyCreateDto) {
        User loggedUser = loginUtilityService.getLoggedInUser();
        Ticket ticket = ticketService.findById(id);

        TicketReply ticketReply = new TicketReply();

        ticketReply.setTicket(ticket);
        ticketReply.setCreatedUser(ticket.getUser());
        ticketReply.setRespondedToUser(loggedUser);
        ticketReply.setParent(null);
        ticketReply.setContent(ticketReplyCreateDto.content());

        TicketReply createdTicketReply = ticketReplyRepository.save(ticketReply);

        UserResponseDto createdUserResponseDto = new UserResponseDto(
                createdTicketReply.getCreatedUser().getId(),
                createdTicketReply.getCreatedUser().getUsername(),
                createdTicketReply.getCreatedUser().getEmail(),
                createdTicketReply.getCreatedUser().getRole().name()
        );

        UserResponseDto respondedToUserResponseDto = new UserResponseDto(
                createdTicketReply.getRespondedToUser().getId(),
                createdTicketReply.getRespondedToUser().getUsername(),
                createdTicketReply.getRespondedToUser().getEmail(),
                createdTicketReply.getRespondedToUser().getRole().name()
        );

        CategoryResponseDto ticketCategoryResponseDto = new CategoryResponseDto(
                createdTicketReply.getTicket().getCategory().getId(),
                createdTicketReply.getTicket().getCategory().getName(),
                createdTicketReply.getTicket().getCategory().getDescription(),
                createdTicketReply.getTicket().getCategory().getPriority().name()
        );

        TicketResponseDto ticketResponseDto = new TicketResponseDto(
                createdTicketReply.getTicket().getId(),
                createdUserResponseDto,
                ticketCategoryResponseDto,
                createdTicketReply.getTicket().getTitle(),
                createdTicketReply.getTicket().getContent(),
                createdTicketReply.getTicket().getStatus().name()
        );

        return new TicketReplyResponseDto(
                createdTicketReply.getId(),
                ticketResponseDto,
                respondedToUserResponseDto,
                createdTicketReply.getContent(),
                createdTicketReply.getCreatedAt(),
                createdTicketReply.getUpdatedAt()
        );
    }
}
