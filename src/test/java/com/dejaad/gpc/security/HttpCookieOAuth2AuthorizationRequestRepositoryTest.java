package com.dejaad.gpc.security;

import com.dejaad.gpc.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.List;

import static com.dejaad.gpc.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;
import static com.dejaad.gpc.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class HttpCookieOAuth2AuthorizationRequestRepositoryTest {

    private final HttpCookieOAuth2AuthorizationRequestRepository repository = new HttpCookieOAuth2AuthorizationRequestRepository();

    @Test
    void loadAuthorizationRequest_empty_request() {
        var result = repository.loadAuthorizationRequest(mock(HttpServletRequest.class));

        assertNull(result);
    }

    @Test
    void removeAuthorizationRequest_empty_request() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        var result = repository.removeAuthorizationRequest(request, response);

        assertNull(result);
        verify(request, times(1)).getCookies();
    }

    @Test
    void removeAuthorizationRequestCookies_empty_resource() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        repository.removeAuthorizationRequestCookies(request, response);

        verify(request, times(2)).getCookies();
        verify(response, times(0)).addCookie(any());
    }

    @Test
    void removeAuthorizationRequestCookies() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Cookie cookie1 = new Cookie(OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, "123");
        Cookie cookie2 = new Cookie(REDIRECT_URI_PARAM_COOKIE_NAME, "123");
        when(request.getCookies()).thenReturn(List.of(cookie1, cookie2).toArray(new Cookie[1]));

        repository.removeAuthorizationRequestCookies(request, response);

        verify(request, times(2)).getCookies();
        verify(response, times(2)).addCookie(any());
    }

    @Test
    void saveAuthorizationRequest_empty() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        repository.saveAuthorizationRequest(null, request, response);

        verify(request, times(2)).getCookies();
    }

    @Test
    void saveAuthorizationRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        OAuth2AuthorizationRequest authorizationRequest = mock(OAuth2AuthorizationRequest.class);

        when(request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME)).thenReturn("/oauth2/redirect");

        repository.saveAuthorizationRequest(authorizationRequest, request, response);

        verify(response, times(2)).addCookie(any());
    }
}
