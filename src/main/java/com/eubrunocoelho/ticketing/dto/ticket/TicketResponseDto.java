package com.eubrunocoelho.ticketing.dto.ticket;

import com.eubrunocoelho.ticketing.dto.category.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.replies.RepliesResponseDto;
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

        List<RepliesResponseDto> replies,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
