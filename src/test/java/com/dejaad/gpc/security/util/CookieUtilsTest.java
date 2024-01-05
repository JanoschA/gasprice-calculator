package com.dejaad.gpc.security.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
* This class tests the functionality of the CookieUtils class.
*/
class CookieUtilsTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Cookie cookie;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        cookie = new Cookie("User", "123");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(200);
    }

    @Test
    void addSecureCookie_ShouldAddCookieWithCorrectProperties() {
        CookieUtils.addSecureCookie(response, "User", "123", 200);

        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(response, times(1)).addCookie(cookieCaptor.capture());

        verifyCookie(cookie, cookieCaptor.getValue());
    }

    @Test
    void getCookie_ShouldReturnEmptyOptional_WhenNoCookiesPresent() {
        Optional<Cookie> optionalCookie = CookieUtils.getCookie(request, "User");

        assertFalse(optionalCookie.isPresent());
    }

    @Test
    void getCookie_ShouldReturnCorrectCookie_WhenCookieExists() {
        when(request.getCookies()).thenReturn(List.of(cookie).toArray(new Cookie[0]));

        Optional<Cookie> optionalCookie = CookieUtils.getCookie(request, "User");

        assertTrue(optionalCookie.isPresent());
        Cookie retrievedCookie = optionalCookie.get();

        assertEquals("User", retrievedCookie.getName());
        assertEquals("123", retrievedCookie.getValue());
        assertEquals(200, retrievedCookie.getMaxAge());
        assertTrue(retrievedCookie.isHttpOnly());
        assertTrue(retrievedCookie.getSecure());
    }

    @Test
    void getCookie_ShouldReturnEmptyOptional_WhenCookieNameDoesNotMatch() {
        when(request.getCookies()).thenReturn(List.of(cookie).toArray(new Cookie[0]));

        Optional<Cookie> optionalCookie = CookieUtils.getCookie(request, "User1");

        assertFalse(optionalCookie.isPresent());
    }

    @Test
    void deleteCookie_ShouldAddCookieWithMaxAgeZero_WhenCookiesAreNull() {
        when(request.getCookies()).thenReturn(new Cookie[] { cookie });

        CookieUtils.deleteCookie(request, response, "User");

        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(response, times(1)).addCookie(cookieCaptor.capture());

        Cookie expectedCookie = new Cookie("User", "");
        expectedCookie.setMaxAge(0);
        expectedCookie.setPath("/");
        expectedCookie.setHttpOnly(true);
        expectedCookie.setSecure(true);

        verifyCookie(expectedCookie, cookieCaptor.getValue());
    }

    @Test
    void deleteCookie_ShouldThrowException_WhenCookieNameDoesNotExist() {
        when(request.getCookies()).thenReturn(List.of(cookie).toArray(new Cookie[0]));

        assertThrows(NoSuchElementException.class, () -> CookieUtils.deleteCookie(request, response, "WrongName"));

        verify(response, never()).addCookie(any(Cookie.class));
    }

    @Test
    void deleteCookie_ShouldAddCookieWithMaxAgeZero_WhenCookieExists() {
        when(request.getCookies()).thenReturn(List.of(cookie).toArray(new Cookie[0]));

        CookieUtils.deleteCookie(request, response, "User");

        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(response, times(1)).addCookie(cookieCaptor.capture());

        Cookie capturedCookie = cookieCaptor.getValue();
        assertEquals("User", capturedCookie.getName());
        assertEquals(0, capturedCookie.getMaxAge());
    }

    @Test
    void serialize_ShouldReturnSerializedString_WhenGivenValidCookie() {
        String serialized = CookieUtils.serialize(cookie);

        Cookie deserializedCookie = deserialize(serialized);

        assertNotNull(deserializedCookie);
        assertEquals(cookie.getName(), deserializedCookie.getName());
        assertEquals(cookie.getValue(), deserializedCookie.getValue());
    }

    @Test
    void getTokenCookieName_ShouldReturnGPCToken() {
        assertEquals("GPC_TOKEN", CookieUtils.getTokenCookieName());
    }

    @Test
    void deserialize_ShouldReturnOriginalObject_WhenGivenSerializedCookie() {
        String originalValue = "123";
        String serializedValue = CookieUtils.serialize(originalValue);
        Cookie cookie = new Cookie("test", serializedValue);

        String deserializedValue = CookieUtils.deserialize(cookie, String.class);

        // Check that the deserialized object is the same as the original object
        assertEquals(originalValue, deserializedValue);
    }

    @Test
    void deserialize_ShouldThrowRuntimeException_WhenGivenInvalidCookie() {
        Cookie cookie = new Cookie("test", "invalid value");
        assertThrows(RuntimeException.class, () -> CookieUtils.deserialize(cookie, String.class));
    }

    @Test
    void deserialize_ShouldThrowException_WhenClassMismatch() {
        String originalValue = "123";
        String serializedValue = CookieUtils.serialize(originalValue);
        Cookie cookie = new Cookie("test", serializedValue);

        assertThrows(RuntimeException.class, () -> CookieUtils.deserialize(cookie, Cookie.class));
    }

    @Test
    void constructor_ShouldBePrivate() throws NoSuchMethodException {
        Constructor<CookieUtils> constructor = CookieUtils.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof IllegalAccessException);
        assertEquals("This is a utility class and cannot be instantiated", exception.getCause().getMessage());
    }

    /**
     * Helper method to deserialize a cookie.
     */
    private Cookie deserialize(String serialized) {
        try {
            byte[] data = Base64.getDecoder().decode(serialized);
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            return (Cookie) object;
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not deserialize object", e);
        }
    }

    /**
     * Helper method to verify that two cookies are equal.
     */
    private void verifyCookie(Cookie expectedCookie, Cookie actualCookie) {
        assertEquals(expectedCookie.getName(), actualCookie.getName());
        assertEquals(expectedCookie.getValue(), actualCookie.getValue());
        assertEquals(expectedCookie.getMaxAge(), actualCookie.getMaxAge());
        assertEquals(expectedCookie.getPath(), actualCookie.getPath());
        assertEquals(expectedCookie.isHttpOnly(), actualCookie.isHttpOnly());
        assertEquals(expectedCookie.getSecure(), actualCookie.getSecure());
    }
}
