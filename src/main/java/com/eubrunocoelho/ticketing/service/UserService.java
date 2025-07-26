package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.dto.user.UserCreateDto;
import com.eubrunocoelho.ticketing.entity.Users;
import com.eubrunocoelho.ticketing.repository.UserRepository;
import com.eubrunocoelho.ticketing.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Users createUser(UserCreateDto userDTO) {
        Users user = new Users();
        String encryptedPassword = passwordEncoder.encode(userDTO.password());

        user.setEmail(userDTO.email());
        user.setUsername(userDTO.username());
        user.setPassword(encryptedPassword);
        user.setRole(Users.Role.ROLE_USER);

        return userRepository.save(user);
    }

    public Users findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Usuário não encontrado. {id}: " + id)
        );
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new ObjectNotFoundException("Usuário não encontrado. {username}: " + username)
        );
    }
}
