package com.eubrunocoelho.ticketing.service.user;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.auth.InvalidCredentialsException;
import com.eubrunocoelho.ticketing.repository.UserRepository;
import com.eubrunocoelho.ticketing.security.principal.AuthenticatedUser;
import com.eubrunocoelho.ticketing.service.user.commom.GetAuthenticatedUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserPrincipalService
{
    private final UserRepository userRepository;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @Transactional( readOnly = true )
    public AuthenticatedUser findMatch( String usernameOrEmail )
    {
        User user = userRepository.findByUsernameOrEmail( usernameOrEmail, usernameOrEmail )
                .orElseThrow( () ->
                        new InvalidCredentialsException( "Credenciais inválidas." )
                );

        GrantedAuthority authority = () -> user.getRole().name();

        Set<GrantedAuthority> authorities = Set.of( authority );

        return new AuthenticatedUser( user, authorities );
    }

    public User getLoggedInUser()
    {
        return getAuthenticatedUserUseCase.getAuthenticatedUser();
    }
}
