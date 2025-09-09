package com.eubrunocoelho.ticketing.web.interceptor;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import com.eubrunocoelho.ticketing.util.ScreenLabelContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class ScreenLabelInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ScreenLabelInterceptor.class);
    private static final String SCREEN_LABEL_FORMAT = "Ticketing API - [%s] [%s]";

    private final UserPrincipalService userPrincipalService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestInfo = String.format("[%s]|%s", request.getMethod(), request.getRequestURI());
        String userInfo = "";

        try {
            User loggedUser = userPrincipalService.getLoggedInUser();

            if (loggedUser != null) {
                userInfo = String.format("%s|%s", loggedUser.getUsername(), loggedUser.getRole().name());
            }
        } catch (Exception ex) {
        }

        String screenLabel = String.format(SCREEN_LABEL_FORMAT, requestInfo, userInfo);
        logger.info(screenLabel);

        ScreenLabelContext.setLabel(screenLabel);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ScreenLabelContext.clear();
    }
}
