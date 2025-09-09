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
public interface ReplyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticket", source = "ticket")
    @Mapping(target = "createdUser", source = "createdUser")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Reply toEntity(ReplyCreateDto dto, Ticket ticket, User createdUser);

    @Named("ReplyToDto")
    @Mapping(target = "ticket", source = "ticket", qualifiedByName = "mapTicketForReply")
    @Mapping(target = "createdUser", source = "createdUser", qualifiedByName = "mapUserForReply")
    @Mapping(target = "respondedToUser", source = "respondedToUser", qualifiedByName = "mapUserForReply")
    @Mapping(target = "parent", source = "parent", qualifiedByName = "mapReplyParent")
    ReplyResponseDto toDto(Reply reply);

    void updateReplyFromDto(ReplyUpdateDto replyUpdateDto, @MappingTarget Reply reply);

    @Named("mapReplyParent")
    default ReplyResponseDto mapReplyParent(Reply replyParent) {
        return (replyParent == null)
                ? null
                : new ReplyResponseDto(
                replyParent.getId(),
                null,
                null,
                null,
                null,
                replyParent.getContent(),
                replyParent.getCreatedAt(),
                replyParent.getUpdatedAt()
        );
    }
}
