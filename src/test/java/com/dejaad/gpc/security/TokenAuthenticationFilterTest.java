package com.dejaad.gpc.security;

import com.dejaad.gpc.exception.ResourceNotFoundException;
import com.dejaad.gpc.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class TokenAuthenticationFilterTest {

    public static final String JWT = "123456789";
    public static final String USER_ID = "9999";
    private final TokenProvider tokenProvider = mock(TokenProvider.class);
    private final UserService userService = mock(UserService.class);

    private final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(tokenProvider, userService);

    @Test
    void doFilterInternal() throws ServletException, IOException {
        HttpServletRequest request = mock();
        HttpServletResponse response = mock();
        FilterChain filterChain = mock();

        when(request.getCookies()).thenReturn(new Cookie[0]);

        filter.doFilterInternal(request, response, filterChain);

        verify(tokenProvider, times(0)).validateToken(any());
    }

    @Test
    void doFilterInternal_with_cookie() throws ServletException, IOException {
        HttpServletRequest request = mock();
        HttpServletResponse response = mock();
        FilterChain filterChain = mock();

        Cookie cookie = new Cookie("GPC_TOKEN", JWT);
        when(request.getCookies()).thenReturn(List.of(cookie).toArray(new Cookie[0]));

        when(tokenProvider.validateToken(JWT)).thenReturn(true);
        when(tokenProvider.getUserIdFromToken(JWT)).thenReturn(USER_ID);

        when(userService.loadUserById(USER_ID)).thenReturn(new UserPrincipal(
                USER_ID,
                "no-email",
                "noop",
                Collections.emptyList()
        ));

        filter.doFilterInternal(request, response, filterChain);

        verify(tokenProvider, times(1)).validateToken(JWT);
        verify(tokenProvider, times(1)).getUserIdFromToken(JWT);
        verify(userService, times(1)).loadUserById(USER_ID);
    }

    @Test
    void doFilterInternal_with_cookie_user_not_found() throws ServletException, IOException {
        HttpServletRequest request = mock();
        HttpServletResponse response = mock();
        FilterChain filterChain = mock();

        Cookie cookie = new Cookie("GPC_TOKEN", JWT);
        when(request.getCookies()).thenReturn(List.of(cookie).toArray(new Cookie[0]));

        when(tokenProvider.validateToken(JWT)).thenReturn(true);
        when(tokenProvider.getUserIdFromToken(JWT)).thenReturn(USER_ID);

        when(userService.loadUserById(USER_ID)).thenThrow(new ResourceNotFoundException("User", "id", USER_ID));

        filter.doFilterInternal(request, response, filterChain);

        verify(tokenProvider, times(1)).validateToken(JWT);
        verify(tokenProvider, times(1)).getUserIdFromToken(JWT);
        verify(userService, times(1)).loadUserById(USER_ID);
    }
}
