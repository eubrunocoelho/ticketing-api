package com.eubrunocoelho.ticketing.dto.ticket.replies;

import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;

import java.time.LocalDateTime;

public record TicketRepliesResponseDto(

        Long id,

        UserResponseDto createdUser,

        UserResponseDto respondedToUser,

        String content,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
