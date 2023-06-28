package com.dejaad.gpc.security.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CookieUtilsTest {

    @Test
    void addCookie() {
        HttpServletResponse response = mock(HttpServletResponse.class);

        CookieUtils.addCookie(response, "User", "123", 200);

        Cookie cookie = new Cookie("User", "123");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(200);
        verify(response, times(1)).addCookie(cookie);
    }

    @Test
    void getCookie_empty() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        Optional<Cookie> optionalCookie = CookieUtils.getCookie(request, "User");

        assertFalse(optionalCookie.isPresent());
    }

    @Test
    void getCookie_successful() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie cookie = new Cookie("User", "123");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(200);
        when(request.getCookies()).thenReturn(List.of(cookie).toArray(new Cookie[0]));

        Optional<Cookie> optionalCookie = CookieUtils.getCookie(request, "User");

        assertTrue(optionalCookie.isPresent());
        assertEquals("User", optionalCookie.get().getName());
    }

    @Test
    void getCookie_not_correct_name() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie cookie = new Cookie("User", "123");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(200);
        when(request.getCookies()).thenReturn(List.of(cookie).toArray(new Cookie[0]));

        Optional<Cookie> optionalCookie = CookieUtils.getCookie(request, "User1");

        assertFalse(optionalCookie.isPresent());
    }

    @Test
    void deleteCookie_cookies_are_null() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        CookieUtils.deleteCookie(request, response, "User");
    }

    @Test
    void deleteCookie_cookies_with_wrong_name() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Cookie cookie = new Cookie("User", "123");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(200);

        when(request.getCookies()).thenReturn(List.of(cookie).toArray(new Cookie[0]));

        CookieUtils.deleteCookie(request, response, "User123");
    }

    @Test
    void deleteCookie() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Cookie cookie = new Cookie("User", "123");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(200);

        when(request.getCookies()).thenReturn(List.of(cookie).toArray(new Cookie[0]));

        CookieUtils.deleteCookie(request, response, "User");

        verify(response, times(1)).addCookie(any());
    }

    @Test
    void serialize() {
        String serialize = CookieUtils.serialize(new Cookie("User", "123"));
        assertNotNull(serialize);
    }

    @Test
    void getCookieName() {
        assertEquals("GPC_TOKEN", CookieUtils.getTokenCookieName());
    }
}
