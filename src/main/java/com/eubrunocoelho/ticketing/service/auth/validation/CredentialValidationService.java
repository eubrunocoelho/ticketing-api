package com.eubrunocoelho.ticketing.service.auth.validation;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.auth.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialValidationService {

    private final PasswordEncoder passwordEncoder;

    public void validate(User user, String password) {
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Credenciais inválidas.");
        }
    }
}
