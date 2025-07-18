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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoginUtilityService {

    private final UserRepository userRepository;

    public AuthenticatedUser findMatch(String username) {
        List<Users> users = userRepository.findByUsername(username);

        if (users == null || users.size() == 0) {
            throw new UsernameNotFoundException(
                    "Usuário não encontrado. {username}: " + username
            );
        }

        Users user = users.get(0);

        GrantedAuthority authority = () -> user.getRole().name();

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(Arrays.asList(authority));

        return new AuthenticatedUser(user, authorities);
    }

    public Users getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }

        return ((AuthenticatedUser) authentication.getPrincipal()).getUser();
    }
}
