package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.dto.UserCreateDto;
import com.eubrunocoelho.ticketing.entity.Users;
import com.eubrunocoelho.ticketing.repository.UserRepository;
import com.eubrunocoelho.ticketing.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Users findById(Long id) {
        Optional<Users> user = userRepository.findById(id);

        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado. {id}: " + id
        ));
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public Users createUser(UserCreateDto userDTO) {
        Users user = new Users();

        user.setEmail(userDTO.email());
        user.setUsername(userDTO.username());
        user.setPassword(userDTO.password());
        user.setRole(Users.Role.ROLE_USER);

        Users createdUser = userRepository.save(user);

        logger.info("Usuário cadastrado. {id}: " + createdUser.getId());

        return createdUser;
    }
}
