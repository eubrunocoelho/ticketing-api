package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.config.MapperConfiguration;
import com.eubrunocoelho.ticketing.dto.auth.AuthResponseDto;
import com.eubrunocoelho.ticketing.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = MapperConfiguration.class
)
public interface AuthMapper {

    @Mapping(target = "authToken", source = "token")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "role", expression = "java(user.getRole().name())")
    AuthResponseDto toDto(User user, String token);
}
