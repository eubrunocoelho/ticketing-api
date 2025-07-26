package com.eubrunocoelho.ticketing.dto.tickets;

import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.Categories;
import com.eubrunocoelho.ticketing.entity.Users;

public record TicketResponseDto(

        Long id,

        UserResponseDto user,

        String title,

        String description,

        Categories category
) {
}
