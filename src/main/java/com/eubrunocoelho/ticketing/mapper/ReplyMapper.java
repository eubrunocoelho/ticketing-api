package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TicketMapper.class, UserMapper.class})
public interface ReplyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticket", source = "ticket")
    @Mapping(target = "createdUser", source = "createdUser")
    @Mapping(target = "parent", source = "parent")
    Reply toEntity(ReplyCreateDto dto, Ticket ticket, User createdUser, Reply parent);

    @Mapping(target = "createdUser", expression = "java(toCreatedUserDto(reply.getCreatedUser()))")
    @Mapping(target = "parent", expression = "java(toParentDto(reply.getParent()))")
    ReplyResponseDto toDto(Reply reply);

    default UserResponseDto toCreatedUserDto(User createdUser) {
        return new UserResponseDto(
                null,
                createdUser.getUsername(),
                createdUser.getEmail(),
                null
        );
    }

    default ReplyResponseDto toParentDto(Reply parent) {
        return (parent.getParent() == null) ? null : new ReplyResponseDto(
                parent.getId(),
                null,
                null,
                null,
                parent.getContent()
        );
    }
}
