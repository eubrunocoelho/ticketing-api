package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.config.CentralMapperConfig;
import com.eubrunocoelho.ticketing.dto.ticket.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketUpdateDto;
import com.eubrunocoelho.ticketing.entity.Category;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        config = CentralMapperConfig.class,
        uses = {UserMapper.class, CategoryMapper.class}
)
public interface TicketMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "status", expression = "java(defaultStatus())")
    Ticket toEntity(TicketCreateDto dto, User user, Category category);

    @Named("ticketWithoutReplies")
    @Mapping(target = "replies", expression = "java(null)")
    TicketResponseDto toDto(Ticket ticket);

    @Named("ticketWithReplies")
    TicketResponseDto toDtoWithReplies(Ticket ticket);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTicketFromDto(TicketUpdateDto ticketUpdateDto, @MappingTarget Ticket ticket);

    default Ticket.Status defaultStatus() {
        return Ticket.Status.OPEN;
    }
}
