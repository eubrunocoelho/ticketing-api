package com.eubrunocoelho.ticketing.dto.ticket;

import com.eubrunocoelho.ticketing.dto.category.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.replies.TicketRepliesResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public record TicketResponseDto(
        Long id,

        UserResponseDto user,

        CategoryResponseDto category,

        String title,

        String content,

        String status,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        List<TicketRepliesResponseDto> replies
)
{
}
