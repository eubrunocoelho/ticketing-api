package com.eubrunocoelho.ticketing.service.user;

import com.eubrunocoelho.ticketing.dto.user.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.user.UserFilterDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserRoleUpdateDto;
import com.eubrunocoelho.ticketing.dto.user.UserUpdateDto;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.mapper.UserMapper;
import com.eubrunocoelho.ticketing.repository.UserRepository;
import com.eubrunocoelho.ticketing.exception.entity.ObjectNotFoundException;
import com.eubrunocoelho.ticketing.repository.specification.UserSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final UserSpecificationBuilder userSpecificationBuilder;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto createUser( UserCreateDto userDTO )
    {
        User user = userMapper.toEntity( userDTO, passwordEncoder );
        User createdUser = userRepository.save( user );

        return userMapper.toDto( createdUser );
    }

    @Transactional( readOnly = true )
    public Page<UserResponseDto> findAllPaged( UserFilterDto filter, Pageable pageable )
    {
        Specification<User> specification = userSpecificationBuilder.build( filter );

        Page<User> userPaged = userRepository.findAll( specification, pageable );

        return userPaged.map( userMapper::toDto );
    }

    @Transactional( readOnly = true )
    public UserResponseDto findById( Long id )
    {
        User user = userRepository
                .findById( id )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Usuário não encontrado. {id}: " + id
                        )
                );

        return userMapper.toDto( user );
    }

    @Transactional( readOnly = true )
    public User findByUsernameOrEmail( String usernameOrEmail )
    {
        return userRepository.findByUsernameOrEmail( usernameOrEmail, usernameOrEmail )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Usuário não encontrado. {username/email}: " + usernameOrEmail
                        )
                );
    }

    @Transactional
    public UserResponseDto updateUser( Long id, UserUpdateDto userUpdateDto )
    {
        User user = userRepository
                .findById( id )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Usuário não econtrado. {id}: " + id
                        )
                );

        userMapper.updateUserFromDto( userUpdateDto, passwordEncoder , user );
        User updatedUser = userRepository.save( user );

        return userMapper.toDto( updatedUser );
    }

    @Transactional
    public UserResponseDto updateUserRole( Long id, UserRoleUpdateDto userRoleUpdateDto )
    {
        User user = userRepository
                .findById( id )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Usuário não econtrado. {id}: " + id
                        )
                );

        userMapper.updateUserRoleFromDto( userRoleUpdateDto, user );
        User updatedUserRole = userRepository.save( user );

        return userMapper.toDto( updatedUserRole );
    }
}
