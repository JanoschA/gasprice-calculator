package com.dejaad.gpc.service.oauth2;

import com.dejaad.gpc.exception.BadRequestException;
import com.dejaad.gpc.security.TokenProvider;
import com.dejaad.gpc.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.dejaad.gpc.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.dejaad.gpc.security.oauth2.service.AuthorizationRedirectService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OAuth2AuthenticationSuccessHandlerTest {

    private final TokenProvider tokenProvider = mock(TokenProvider.class);
    private final AuthorizationRedirectService authorizationRedirectService = mock(AuthorizationRedirectService.class);
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository
            = mock(HttpCookieOAuth2AuthorizationRequestRepository.class);

    private final OAuth2AuthenticationSuccessHandler handler = new OAuth2AuthenticationSuccessHandler(
            tokenProvider, authorizationRedirectService, httpCookieOAuth2AuthorizationRequestRepository
    );

    @Test
    void onAuthenticationSuccess_successful() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        handler.onAuthenticationSuccess(request, response, authentication);

        verify(tokenProvider, times(1)).createToken(authentication);
        verify(authorizationRedirectService, times(0)).isAuthorizedRedirectUri(any());
    }

    @Test
    void onAuthenticationSuccess_successful_with_cookie() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        Cookie cookie = new Cookie(HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME, "/oauth2/redirect");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(200);
        when(request.getCookies()).thenReturn(List.of(cookie).toArray(new Cookie[0]));
        when(authorizationRedirectService.isAuthorizedRedirectUri("/oauth2/redirect")).thenReturn(true);

        handler.onAuthenticationSuccess(request, response, authentication);

        verify(tokenProvider, times(1)).createToken(authentication);
        verify(authorizationRedirectService, times(1)).isAuthorizedRedirectUri(any());
    }

    @Test
    void onAuthenticationSuccess_wrong_redirect_cookie() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        Cookie cookie = new Cookie(HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME, "/oauth2/redirect");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(200);
        when(request.getCookies()).thenReturn(List.of(cookie).toArray(new Cookie[0]));
        when(authorizationRedirectService.isAuthorizedRedirectUri("/oauth3/redirect")).thenReturn(false);

        var exception = assertThrows(BadRequestException.class, () -> handler.onAuthenticationSuccess(request, response, authentication));

        assertEquals("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication", exception.getMessage());
        verify(tokenProvider, times(0)).createToken(authentication);
        verify(authorizationRedirectService, times(1)).isAuthorizedRedirectUri(any());
    }

    @Test
    void onAuthenticationSuccess_response_already_committed() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        when(response.isCommitted()).thenReturn(true);

        handler.onAuthenticationSuccess(request, response, authentication);

        verify(tokenProvider, times(0)).createToken(authentication);
        verify(authorizationRedirectService, times(0)).isAuthorizedRedirectUri(any());
    }
}
