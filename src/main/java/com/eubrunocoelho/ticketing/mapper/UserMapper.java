package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.config.CentralMapperConfig;
import com.eubrunocoelho.ticketing.dto.user.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserRoleUpdateDto;
import com.eubrunocoelho.ticketing.dto.user.UserStatusUpdateDto;
import com.eubrunocoelho.ticketing.dto.user.UserUpdateDto;
import com.eubrunocoelho.ticketing.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
        config = CentralMapperConfig.class
)
public interface UserMapper
{
    @Named( "userToEntity" )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "password", source = "password", qualifiedByName = "encodePassword" )
    @Mapping( target = "role", expression = "java(defaultRole())" )
    @Mapping( target = "status", expression = "java(defaultStatus())" )
    @Mapping( target = "createdAt", ignore = true )
    @Mapping( target = "updatedAt", ignore = true )
    User toEntity( UserCreateDto dto, @Context PasswordEncoder encoder );

    @Named( "userToDto" )
    UserResponseDto toDto( User entity );

    @Named( "updateUserFromDto" )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "password", source = "password", qualifiedByName = "encodePassword" )
    void updateUserFromDto(
            UserUpdateDto userUpdateDto,
            @Context PasswordEncoder encoder,
            @MappingTarget User user
    );

    @Named( "updateUserRoleFromDto" )
    @Mapping( target = "id", ignore = true )
    void updateUserRoleFromDto(
            UserRoleUpdateDto userRoleUpdateDto,
            @MappingTarget User user
    );

    @Named( "updateUserStatusFromDto" )
    @Mapping( target = "id", ignore = true )
    void updateUserStatusFromDto(
            UserStatusUpdateDto userStatusUpdateDto,
            @MappingTarget User user
    );

    @Named( "encodePassword" )
    default String encodePassword( String password, @Context PasswordEncoder encoder )
    {
        return encoder.encode( password );
    }

    @Named( "mapUserForTicket" )
    @Mapping( target = "createdAt", expression = "java(null)" )
    @Mapping( target = "updatedAt", expression = "java(null)" )
    UserResponseDto mapUserForTicket( User user );

    @Named( "mapUserForReply" )
    @Mapping( target = "id", expression = "java(null)" )
    @Mapping( target = "role", expression = "java(null)" )
    @Mapping( target = "status", expression = "java(null)" )
    @Mapping( target = "createdAt", expression = "java(null)" )
    @Mapping( target = "updatedAt", expression = "java(null)" )
    UserResponseDto mapUserForReply( User user );

    default User.Role defaultRole()
    {
        return User.Role.ROLE_USER;
    }

    default User.Status defaultStatus()
    {
        return User.Status.ACTIVE;
    }
}
