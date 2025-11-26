package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.config.CentralMapperConfig;
import com.eubrunocoelho.ticketing.dto.ticket.TicketCreateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketStatusUpdateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketUpdateDto;
import com.eubrunocoelho.ticketing.dto.ticket.replies.TicketRepliesResponseDto;
import com.eubrunocoelho.ticketing.entity.Category;
import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        config = CentralMapperConfig.class,
        uses = {UserMapper.class, CategoryMapper.class}
)
public interface TicketMapper
{
    @Named( "ticketToEntity" )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "user", source = "user" )
    @Mapping( target = "category", source = "category" )
    @Mapping( target = "status", expression = "java(defaultStatus())" )
    Ticket toEntity( TicketCreateDto dto, User user, Category category );

    @Named( "ticketToDto" )
    @Mapping( target = "user", source = "user", qualifiedByName = "userToDto" )
    @Mapping( target = "category", source = "category", qualifiedByName = "mapCategoryForTicket" )
    @Mapping( target = "replies", expression = "java(null)" )
    TicketResponseDto toDto( Ticket ticket );

    @Named( "ticketToDtoWithReplies" )
    @Mapping( target = "category", source = "category", qualifiedByName = "mapCategoryForTicket" )
    @Mapping( target = "replies", source = "ticket.replies", qualifiedByName = "mapTicketRepliesForTicket" )
    TicketResponseDto toDtoWithReplies( Ticket ticket );

    @Named( "updateTicketFromDto" )
    @BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "category", ignore = true )
    void updateTicketFromDto( TicketUpdateDto ticketUpdateDto, @MappingTarget Ticket ticket, Category category );

    @Named( "updateTicketStatusFromDto" )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "category", ignore = true )
    void updateTicketStatusFromDto(
            TicketStatusUpdateDto ticketStatusUpdateDto,
            @MappingTarget Ticket ticket
    );

    @Named( "mapTicketForReply" )
    @Mapping( target = "user", source = "user" )
    @Mapping( target = "category", source = "category", qualifiedByName = "mapCategoryForTicket" )
    @Mapping( target = "replies", expression = "java(null)" )
    TicketResponseDto mapTicketForReply( Ticket ticket );

    @Named( "mapTicketRepliesForTicket" )
    @Mapping( target = "createdUser", source = "reply.createdUser", qualifiedByName = "mapUserForReply" )
    @Mapping( target = "respondedToUser", source = "reply.respondedToUser", qualifiedByName = "mapUserForReply" )
    TicketRepliesResponseDto mapTicketRepliesForTicket( Reply reply );

    default Ticket.Status defaultStatus()
    {
        return Ticket.Status.OPEN;
    }
}
