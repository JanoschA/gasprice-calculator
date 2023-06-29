package com.dejaad.gpc.security.oauth2.service;

import com.dejaad.gpc.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthorizationRedirectServiceTest {

    private final AppConfig appConfig = mock(AppConfig.class);
    private final AuthorizationRedirectService authorizationRedirectService = new AuthorizationRedirectService(appConfig);

    @Test
    void isAuthorizedRedirectUri_successful() {
        String uri = "https://test.com/oauth2/redirect";

        when(appConfig.getAuthorizedRedirectUris()).thenReturn(List.of(
                uri
        ));

        boolean result = authorizationRedirectService.isAuthorizedRedirectUri(uri);

        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "http://test.com/oauth2/redirect",
            "https://test2.com/oauth2/redirect",
            "https://test.com/oauth3/redirect",
            "https://test.com:7070/oauth2/redirect",
    })
    void isAuthorizedRedirectUri_wrong_redirect(String redirectUrl) {
        String uri = "https://test.com:8080/oauth2/redirect";

        when(appConfig.getAuthorizedRedirectUris()).thenReturn(List.of(
                redirectUrl
        ));

        boolean result = authorizationRedirectService.isAuthorizedRedirectUri(uri);

        assertFalse(result);
    }
}
