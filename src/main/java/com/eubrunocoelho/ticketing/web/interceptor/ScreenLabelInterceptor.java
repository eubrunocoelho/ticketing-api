package com.eubrunocoelho.ticketing.web.interceptor;

import com.eubrunocoelho.ticketing.context.ScreenLabelRequestContext;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class ScreenLabelInterceptor implements HandlerInterceptor
{
    private static final Logger LOGGER =
            LoggerFactory.getLogger( ScreenLabelInterceptor.class );

    private static final String SCREEN_LABEL_FORMAT =
            "[%s] - [%s] [%s]";

    @Value( "${spring.application.name}" )
    private String appName;

    private final UserPrincipalService userPrincipalService;

    @Override
    public boolean preHandle(
            @NonNull
            HttpServletRequest request,

            @NonNull
            HttpServletResponse response,

            @NonNull
            Object handler
    )
    {
        String screenLabel = buildScreenLabel( request );

        LOGGER.info( screenLabel );

        ScreenLabelRequestContext.setScreenLabel( screenLabel );
        MDC.put( "screenLabel", screenLabel );

        return true;
    }

    @Override
    public void afterCompletion(
            @NonNull
            HttpServletRequest request,

            @NonNull
            HttpServletResponse response,

            @NonNull
            Object handler,

            Exception ex
    )
    {
        ScreenLabelRequestContext.clear();
        MDC.remove( "screenLabel" );
    }

    private String buildScreenLabel( HttpServletRequest request )
    {
        String requestInfo = String.format(
                "[%s]|%s",
                request.getMethod(),
                request.getRequestURI()
        );

        String userInfo = buildUserInfo();

        return String.format( SCREEN_LABEL_FORMAT, appName, requestInfo, userInfo );
    }

    private String buildUserInfo()
    {
        try
        {
            User user = userPrincipalService.getLoggedInUser();

            if ( user != null )
            {
                return "%s|%s"
                        .formatted(
                                user.getUsername(),
                                user.getRole().name()
                        );
            }
        }
        catch ( Exception ignored )
        {
        }

        return "";
    }
}
