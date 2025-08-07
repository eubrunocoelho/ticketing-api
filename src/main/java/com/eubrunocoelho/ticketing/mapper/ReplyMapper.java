package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TicketMapper.class})
public interface ReplyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "respondedToUser", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "content", source = "dto.content")
    Reply toEntity(ReplyCreateDto dto, Ticket ticket, User createdUser);

    @Mapping(target = "createdUser", source = "createdUser", qualifiedByName = "mapCreatedUser")
    @Mapping(target = "respondedToUser", source = "respondedToUser", qualifiedByName = "mapRespondedToUser")
    @Mapping(target = "parent", source = "parent", qualifiedByName = "mapParentReply")
    ReplyResponseDto toDto(Reply reply);

    @Named("mapCreatedUser")
    default UserResponseDto mapCreatedUser(User createdUser) {
        return new UserResponseDto(
                null,
                createdUser.getUsername(),
                createdUser.getEmail(),
                null
        );
    }

    @Named("mapRespondedToUser")
    default UserResponseDto mapRespondedToUser(User respondedToUser) {
        return new UserResponseDto(
                null,
                respondedToUser.getUsername(),
                respondedToUser.getEmail(),
                null
        );
    }

    @Named("mapParentReply")
    default ReplyResponseDto mapParentReply(Reply parent) {
        return (parent == null) ? null : new ReplyResponseDto(
                parent.getId(),
                null,
                null,
                null,
                null,
                parent.getContent()
        );
    }
}
