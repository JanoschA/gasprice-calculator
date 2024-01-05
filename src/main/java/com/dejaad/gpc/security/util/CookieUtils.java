package com.dejaad.gpc.security.util;

import org.springframework.util.SerializationUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Utility class for handling cookies.
 * This class provides methods for getting, adding, deleting, serializing, and deserializing cookies.
 */
public class CookieUtils {

    private static final String TOKEN_COOKIE = "GPC_TOKEN";

    private CookieUtils() throws IllegalAccessException {
        throw new IllegalAccessException("This is a utility class and cannot be instantiated");
    }

    /**
     * Retrieves a cookie from the request.
     *
     * @param request the HttpServletRequest from which to retrieve the cookie
     * @param name the name of the cookie to retrieve
     * @return an Optional containing the cookie if it exists, or an empty Optional if it does not
     */
    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        return Optional.ofNullable(request.getCookies())
            .stream()
            .flatMap(Arrays::stream)
            .filter(x -> x.getName().equals(name))
            .findFirst();
    }

    /**
     * Adds a secured cookie to the response.
     *
     * @param response the HttpServletResponse to which to add the cookie
     * @param name the name of the cookie to add
     * @param value the value of the cookie to add
     * @param maxAge the maximum age of the cookie in seconds
     */
    public static void addSecureCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * Deletes a cookie from the request and response.
     *
     * @param request the HttpServletRequest from which to delete the cookie
     * @param response the HttpServletResponse to which to add the deletion command
     * @param name the name of the cookie to delete
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) throws NoSuchElementException {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return;
        }

        Cookie cookie = Arrays.stream(cookies)
            .filter(x -> x.getName().equals(name))
            .findFirst()
            .orElseThrow();

        cookie.setValue("");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * Serializes an object to a Base64-encoded string.
     *
     * @param object the object to serialize
     * @return the Base64-encoded string representing the serialized object
     */
    public static String serialize(Object object) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }

    /**
     * Deserializes an object from a cookie.
     *
     * @param cookie the cookie from which to deserialize the object
     * @param clazz the class of the object to deserialize
     * @return the deserialized object
     * @throws RuntimeException if an error occurs during deserialization
     */
    public static <T> T deserialize(Cookie cookie, Class<T> clazz) throws RuntimeException {
        try {
            byte[] data = Base64.getUrlDecoder().decode(cookie.getValue());
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data))) {
                return clazz.cast(objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new RuntimeException("Failed to deserialize object", e);
        }
    }

    /**
     * Returns the name of the token cookie.
     *
     * @return the name of the token cookie
     */
    public static String getTokenCookieName() {
        return TOKEN_COOKIE;
    }
}
