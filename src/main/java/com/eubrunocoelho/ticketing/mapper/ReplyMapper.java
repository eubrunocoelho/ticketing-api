package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.config.CentralMapperConfig;
import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyResponseDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyUpdateDto;
import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(
        config = CentralMapperConfig.class,
        uses = {UserMapper.class, TicketMapper.class}
)
public interface ReplyMapper
{
    @Named( "replyToEntity" )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "ticket", source = "ticket" )
    @Mapping( target = "createdUser", source = "createdUser" )
    @Mapping( target = "respondedToUser", ignore = true )
    @Mapping( target = "parent", ignore = true )
    @Mapping( target = "content", source = "replyCreateDto.content" )
    @Mapping( target = "createdAt", ignore = true )
    @Mapping( target = "updatedAt", ignore = true )
    Reply toEntity( ReplyCreateDto replyCreateDto, Ticket ticket, User createdUser );

    @Named( "replyToDto" )
    @Mapping( target = "ticket", source = "ticket", qualifiedByName = "mapTicketForReply" )
    @Mapping( target = "createdUser", source = "createdUser", qualifiedByName = "mapUserForReply" )
    @Mapping( target = "respondedToUser", source = "respondedToUser", qualifiedByName = "mapUserForReply" )
    @Mapping( target = "parent", source = "parent", qualifiedByName = "mapReplyParent" )
    ReplyResponseDto toDto( Reply reply );

    @Named( "updateReplyFromDto" )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "ticket", ignore = true )
    @Mapping( target = "createdUser", ignore = true )
    @Mapping( target = "respondedToUser", ignore = true )
    @Mapping( target = "parent", ignore = true )
    @Mapping( target = "createdAt", ignore = true )
    @Mapping( target = "updatedAt", ignore = true )
    void updateReplyFromDto( ReplyUpdateDto replyUpdateDto, @MappingTarget Reply reply );

    @Named( "mapReplyParent" )
    @Mapping( target = "ticket", expression = "java(null)" )
    @Mapping( target = "createdUser", expression = "java(null)" )
    @Mapping( target = "respondedToUser", expression = "java(null)" )
    @Mapping( target = "parent", expression = "java(null)" )
    ReplyResponseDto mapReplyParent( Reply replyParent );
}
