package com.eubrunocoelho.ticketing.dto.reply;

import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;

import java.time.LocalDateTime;

public record ReplyResponseDto(
        Long id,

        TicketResponseDto ticket,

        UserResponseDto createdUser,

        UserResponseDto respondedToUser,

        ReplyResponseDto parent,

        String content,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
)
{
}