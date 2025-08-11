package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.dto.ticket.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.entity.Category;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = CentralMapperConfig.class,
        uses = {UserMapper.class, CategoryMapper.class, ReplyMapper.class}
)
public interface TicketMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "status", expression = "java(defaultStatus())")
    Ticket toEntity(TicketCreateDto dto, User user, Category category);

    default Ticket.Status defaultStatus() {
        return Ticket.Status.OPEN;
    }
}
