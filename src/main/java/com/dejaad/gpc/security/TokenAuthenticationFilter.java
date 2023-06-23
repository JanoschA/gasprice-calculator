package com.dejaad.gpc.security;

import com.dejaad.gpc.exception.ResourceNotFoundException;
import com.dejaad.gpc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static com.dejaad.gpc.security.util.CookieUtils.getTokenCookieName;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            setAuthentication(request, jwt);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        Optional<Cookie> authenticationCookie = Arrays.stream(request.getCookies())
                .filter(c -> getTokenCookieName().equals(c.getName())).findFirst();

        return authenticationCookie.map(Cookie::getValue).orElse(null);
    }

    private void setAuthentication(HttpServletRequest request, String jwt) {
        String userId = tokenProvider.getUserIdFromToken(jwt);

        UserDetails userDetails;
        try {
            userDetails = userService.loadUserById(userId);
        } catch (ResourceNotFoundException ex) {
            log.error("Could not set user authentication in security context", ex);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
