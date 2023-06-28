package com.dejaad.gpc.service.oauth2;

import com.dejaad.gpc.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.dejaad.gpc.security.oauth2.OAuth2AuthenticationFailureHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class OAuth2AuthenticationFailureHandlerTest {

    private final HttpCookieOAuth2AuthorizationRequestRepository repository = mock(HttpCookieOAuth2AuthorizationRequestRepository.class);
    private final OAuth2AuthenticationFailureHandler handler = new OAuth2AuthenticationFailureHandler(repository);

    @Test
    void onAuthenticationFailure() throws IOException {
        handler.onAuthenticationFailure(mock(HttpServletRequest.class), mock(HttpServletResponse.class), mock(AuthenticationException.class));

        verify(repository, times(1)).removeAuthorizationRequestCookies(any(), any());
    }
}
