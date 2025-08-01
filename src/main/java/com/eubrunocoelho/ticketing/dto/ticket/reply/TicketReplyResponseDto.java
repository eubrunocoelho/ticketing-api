package com.eubrunocoelho.ticketing.dto.ticket.reply;

import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;

import java.time.LocalDateTime;

public record TicketReplyResponseDto(

        Long id,

        TicketResponseDto ticket,

        UserResponseDto respondedToUser,

        String content,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
