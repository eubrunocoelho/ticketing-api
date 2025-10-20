package com.eubrunocoelho.ticketing.service.user;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.repository.UserRepository;
import com.eubrunocoelho.ticketing.security.principal.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserPrincipalService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public AuthenticatedUser findMatch(String usernameOrEmail) {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuário não encontrado. {username/email}: " + usernameOrEmail
                ));

        GrantedAuthority authority = () -> user.getRole().name();

        Set<GrantedAuthority> authorities = Set.of(authority);

        return new AuthenticatedUser(user, authorities);
    }

    // REFACTOR
    public User getLoggedInUser() {
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
