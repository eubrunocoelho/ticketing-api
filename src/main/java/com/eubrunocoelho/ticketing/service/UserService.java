package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.dto.user.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.mapper.UserMapper;
import com.eubrunocoelho.ticketing.repository.UserRepository;
import com.eubrunocoelho.ticketing.exception.entity.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto createUser(UserCreateDto userDTO) {
        User user = userMapper.toEntity(userDTO, passwordEncoder);
        User createdUser = userRepository.save(user);

        return userMapper.toDto(createdUser);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Usuário não encontrado. {id}: " + id)
        );
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new ObjectNotFoundException("Usuário não encontrado. {username}: " + username)
        );
    }
}
