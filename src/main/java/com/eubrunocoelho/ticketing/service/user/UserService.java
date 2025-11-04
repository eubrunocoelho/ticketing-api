package com.eubrunocoelho.ticketing.service.user;

import com.eubrunocoelho.ticketing.dto.user.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserUpdateDto;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.mapper.UserMapper;
import com.eubrunocoelho.ticketing.repository.UserRepository;
import com.eubrunocoelho.ticketing.exception.entity.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
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
    public List<UserResponseDto> findAll()
    {
        return userRepository
                .findAll()
                .stream()
                .map( userMapper::toDto )
                .toList();
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
}
