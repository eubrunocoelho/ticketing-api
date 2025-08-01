package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.dto.user.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.Users;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.eubrunocoelho.ticketing.util.EnumUtil.getEnumValueOrThrow;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "role", expression = "java(defaultRole())")
    Users toEntity(UserCreateDto dto, @Context PasswordEncoder encoder);

    UserResponseDto toDto(Users entity);

    @Named("encodePassword")
    default String encodePassword(String password, @Context PasswordEncoder encoder) {
        return encoder.encode(password);
    }

    default Users.Role defaultRole() {
        return Users.Role.ROLE_USER;
    }
}
