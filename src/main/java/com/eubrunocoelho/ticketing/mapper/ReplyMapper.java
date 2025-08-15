package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.config.CentralMapperConfig;
import com.eubrunocoelho.ticketing.dto.category.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyResponseDto;
import com.eubrunocoelho.ticketing.dto.reply.ReplyUpdateDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketResponseDto;
import com.eubrunocoelho.ticketing.dto.ticket.replies.TicketRepliesResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.Category;
import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(
        config = CentralMapperConfig.class,
        uses = {UserMapper.class}
)
public interface ReplyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticket", source = "ticket")
    @Mapping(target = "createdUser", source = "createdUser")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Reply toEntity(ReplyCreateDto dto, Ticket ticket, User createdUser);

    @Mapping(target = "ticket", source = "ticket", qualifiedByName = "mapReplyTicket")
    @Mapping(target = "createdUser", source = "createdUser", qualifiedByName = "mapUserResponse")
    @Mapping(target = "respondedToUser", source = "respondedToUser", qualifiedByName = "mapUserResponse")
    @Mapping(target = "parent", source = "parent", qualifiedByName = "mapParentReply")
    ReplyResponseDto toDto(Reply reply);

    @Mapping(target = "createdUser", source = "createdUser", qualifiedByName = "mapUserResponse")
    @Mapping(target = "respondedToUser", source = "respondedToUser", qualifiedByName = "mapUserResponse")
    TicketRepliesResponseDto toTicketRepliesDto(Reply reply);

    void updateReplyFromDto(ReplyUpdateDto replyUpdateDto, @MappingTarget Reply reply);

    @Named("mapUserResponse")
    default UserResponseDto mapUserResponse(User createdUser) {
        return new UserResponseDto(
                null,
                createdUser.getUsername(),
                createdUser.getEmail(),
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
                parent.getContent(),
                parent.getCreatedAt(),
                parent.getUpdatedAt()
        );
    }

    private CategoryResponseDto mapReplyTicketCategory(Category category) {
        return new CategoryResponseDto(
                null,
                category.getName(),
                category.getDescription(),
                category.getPriority().name()
        );
    }

    @Named("mapReplyTicket")
    default TicketResponseDto mapReplyTicket(Ticket ticket) {
        return (ticket == null) ? null : new TicketResponseDto(
                ticket.getId(),
                mapUserResponse(
                        ticket.getUser()
                ),
                mapReplyTicketCategory(
                        ticket.getCategory()
                ),
                ticket.getTitle(),
                ticket.getContent(),
                ticket.getStatus().name(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt(),
                null
        );
    }
}
