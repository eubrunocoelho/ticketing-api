package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.config.CentralMapperConfig;
import com.eubrunocoelho.ticketing.dto.auth.AuthResponseDto;
import com.eubrunocoelho.ticketing.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
        config = CentralMapperConfig.class
)
public interface AuthMapper
{
    @Named( "authToDto" )
    @Mapping( target = "authToken", source = "token" )
    @Mapping( target = "username", source = "user.username" )
    @Mapping( target = "email", source = "user.email" )
    @Mapping( target = "role", expression = "java(user.getRole().name())" )
    AuthResponseDto toDto( User user, String token );
}
