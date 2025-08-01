package com.eubrunocoelho.ticketing.dto.ticket;

import com.eubrunocoelho.ticketing.dto.category.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;

public record TicketResponseDto(

        Long id,

        UserResponseDto user,

        CategoryResponseDto category,

        String title,

        String content,

        String status
) {
}
