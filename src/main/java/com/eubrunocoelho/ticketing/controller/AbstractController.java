package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.service.user.LoginUtilityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
public abstract class AbstractController extends BaseController {

    protected static final String SCREEN_LABEL = "Ticketing API - [%s] [%s]";

    @Autowired
    protected LoginUtilityService loginUtilityService;

    protected String getRequest() {
        HttpServletRequest request = (
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes()
        ).getRequest();

        return String.format("%s|\"%s\"", request.getMethod(), request.getRequestURI());
    }

    protected String getLoggedUser() {
        User loggedUser = loginUtilityService.getLoggedInUser();

        return String.format("%s|%s", loggedUser.getUsername(), loggedUser.getRole().name());
    }

    protected String getScreenLabel(boolean isAuthenticated) {
        String request = getRequest();
        String loggedUser = (isAuthenticated) ? getLoggedUser() : "";

        return String.format(SCREEN_LABEL, request, loggedUser);
    }
}
