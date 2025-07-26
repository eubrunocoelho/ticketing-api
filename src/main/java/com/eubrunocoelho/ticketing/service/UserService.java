package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.dto.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.Users;
import com.eubrunocoelho.ticketing.repository.UserRepository;
import com.eubrunocoelho.ticketing.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String SCREEN_LABEL = "Ticketing API - [%s] [%s]";

    private final UserRepository userRepository;
    private final LoginUtilityService loginUtilityService;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto findById(Long id) {
        String label = String.format(
                SCREEN_LABEL,
                "",
                ""
        );

        Users user = userRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Usuário não encontrado. {id}: " + id)
        );

        UserResponseDto responseDto = new UserResponseDto(
                label,
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );

        return responseDto;
    }

    public Users findByUsername(String username) {
        Users user = userRepository.findByUsername(username).orElseThrow(() ->
            new ObjectNotFoundException("Usuário não encontrado. {username}: " + username)
        );

        return user;
    }

    public UserResponseDto createUser(UserCreateDto userDTO) {
        String label = String.format(
                SCREEN_LABEL,
                "",
                ""
        );

        Users user = new Users();

        String encryptedPassword = passwordEncoder.encode(userDTO.password());

        user.setEmail(userDTO.email());
        user.setUsername(userDTO.username());
        user.setPassword(encryptedPassword);
        user.setRole(Users.Role.ROLE_USER);

        Users newUser = userRepository.save(user);

        UserResponseDto responseDto = new UserResponseDto(
                label,
                newUser.getId(),
                newUser.getUsername(),
                newUser.getEmail()
        );

        return responseDto;
    }
}
