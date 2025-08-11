package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.dto.user.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
        config = CentralMapperConfig.class
)
public interface UserMapper {

    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "role", expression = "java(defaultRole())")
    User toEntity(UserCreateDto dto, @Context PasswordEncoder encoder);

    UserResponseDto toDto(User entity);

    @Named("encodePassword")
    default String encodePassword(String password, @Context PasswordEncoder encoder) {
        return encoder.encode(password);
    }

    default User.Role defaultRole() {
        return User.Role.ROLE_USER;
    }
}
