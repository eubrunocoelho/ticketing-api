package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.dto.UserCreateDTO;
import com.eubrunocoelho.ticketing.entity.Users;
import com.eubrunocoelho.ticketing.repository.UserRepository;
import com.eubrunocoelho.ticketing.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Users createUser(UserCreateDTO userDTO) {
        Users user = new Users();
        user.setEmail(userDTO.email());
        user.setUsername(userDTO.username());
        user.setPassword(userDTO.password());
        user.setRole(Users.Role.ROLE_USER);

        return userRepository.save(user);
    }

    public Users findById(Long id) {
        Optional<Users> user = userRepository.findById(id);

        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado. {id}: " + id
        ));
    }

    public Users findByUsername(String username) {
        List<Users> users = userRepository.findByUsername(username);

        return users.isEmpty() ? null : users.get(0);
    }
}
