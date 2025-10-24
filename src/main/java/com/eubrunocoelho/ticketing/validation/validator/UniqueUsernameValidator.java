package com.eubrunocoelho.ticketing.validation.validator;

import com.eubrunocoelho.ticketing.validation.annotation.UniqueUsername;
import com.eubrunocoelho.ticketing.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String>
{
    private final UserRepository userRepository;

    @Override
    public boolean isValid( String username, ConstraintValidatorContext context )
    {
        return !userRepository.existsByUsername( username );
    }
}
