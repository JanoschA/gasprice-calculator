package com.dejaad.gpc.model.oauth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthProviderTest {

    @Test
    void shouldContainExpectedNumberOfValues() {
        assertEquals(2, AuthProvider.values().length);
    }

    @Test
    void shouldContainExpectedValues() {
        assertEquals(AuthProvider.GOOGLE, AuthProvider.valueOf("GOOGLE"));
        assertEquals(AuthProvider.GITHUB, AuthProvider.valueOf("GITHUB"));
    }

    @Test
    void shouldThrowExceptionForInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> AuthProvider.valueOf("google"));
        assertThrows(IllegalArgumentException.class, () -> AuthProvider.valueOf("github"));
    }
}