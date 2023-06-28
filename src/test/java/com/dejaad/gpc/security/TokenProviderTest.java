package com.dejaad.gpc.security;

import com.dejaad.gpc.config.AppConfig;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TokenProviderTest {

    private final AppConfig appConfig = mock(AppConfig.class);

    private final TokenProvider tokenProvider = new TokenProvider(appConfig);

    private final Authentication auth = mock(Authentication.class);

    public TokenProviderTest() throws JOSEException {
        UserPrincipal userPrincipal = mock(UserPrincipal.class);

        when(auth.getPrincipal()).thenReturn(userPrincipal);
        when(userPrincipal.getId()).thenReturn("123456789");

        when(appConfig.getTokenExpirationMsec()).thenReturn(864000000L);
    }

    @Test
    void getToken_not_null() {
        String token = tokenProvider.createToken(auth);

        assertNotNull(token);
    }

    @Test
    void getUserIdFromToken_same_pricipal_id() {
        String token = tokenProvider.createToken(auth);

        String userPrincipalId = tokenProvider.getUserIdFromToken(token);

        assertEquals("123456789", userPrincipalId);
    }

    @Test
    void validateToken_correct_token() {
        String token = tokenProvider.createToken(auth);

        boolean result = tokenProvider.validateToken(token);

        assertTrue(result);
    }

    @Test
    void validateToken_expired_token() {
        when(appConfig.getTokenExpirationMsec()).thenReturn(0L);

        String token = tokenProvider.createToken(auth);

        boolean result = tokenProvider.validateToken(token);

        assertFalse(result);
    }

    @Test
    void validateToken_wrong_token() {
        boolean result = tokenProvider.validateToken("wrong_token");

        assertFalse(result);
    }
}
