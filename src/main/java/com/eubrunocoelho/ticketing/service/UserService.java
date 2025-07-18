package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.dto.UserCreateDTO;
import com.eubrunocoelho.ticketing.entity.Users;
import com.eubrunocoelho.ticketing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users createUser(UserCreateDTO userDTO) {
        Users user = new Users();
        user.setEmail(userDTO.email());
        user.setUsername(userDTO.username());
        user.setPassword(userDTO.password());
        user.setRole(Users.Role.ROLE_USER);

        return userRepository.save(user);
    }
}
