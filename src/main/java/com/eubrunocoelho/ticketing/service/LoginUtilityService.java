package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.authentication.AuthenticatedUser;
import com.eubrunocoelho.ticketing.entity.Users;
import com.eubrunocoelho.ticketing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoginUtilityService {

    private final UserRepository userRepository;

    public AuthenticatedUser findMatch(String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuário não encontrado. {username}: " + username
                ));

        GrantedAuthority authority = () -> user.getRole().name();

        Set<GrantedAuthority> authorities = Set.of(authority);

        return new AuthenticatedUser(user, authorities);
    }

    public Users getLoggedInUser() {
        return Optional.ofNullable(
                        SecurityContextHolder.getContext().getAuthentication()
                )
                .map(Authentication::getPrincipal)
                .filter(AuthenticatedUser.class::isInstance)
                .map(AuthenticatedUser.class::cast)
                .map(AuthenticatedUser::getUser)
                .orElseThrow(
                        () -> new IllegalStateException("Usuário não está autenticado.")
                );
    }
}
