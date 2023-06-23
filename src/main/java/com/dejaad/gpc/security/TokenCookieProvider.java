package com.dejaad.gpc.security;

import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenCookieProvider {

    private static final String TOKEN_COOKIE = "GPC_TOKEN";

    public Cookie createTokenCookie(String token) {
        var cookie = new Cookie(TOKEN_COOKIE, token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(200); // TODO: change the max age

        return cookie;
    }

    public static String getTokenCookieName() {
        return TOKEN_COOKIE;
    }
}
