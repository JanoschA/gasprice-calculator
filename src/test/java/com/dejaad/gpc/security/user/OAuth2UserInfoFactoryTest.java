package com.dejaad.gpc.security.user;

import com.dejaad.gpc.exception.OAuth2AuthenticationProcessingException;
import com.dejaad.gpc.security.oauth2.user.GitHubOAuth2UserInfo;
import com.dejaad.gpc.security.oauth2.user.GoogleOAuth2UserInfo;
import com.dejaad.gpc.security.oauth2.user.OAuth2UserInfoFactory;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.dejaad.gpc.domain.oauth.AuthProvider.GITHUB;
import static com.dejaad.gpc.domain.oauth.AuthProvider.GOOGLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OAuth2UserInfoFactoryTest {

    @Test
    void getOAuth2UserInfo_GOOGLE() {
        Map<String, Object> map = new HashMap<>();
        map.put("sub", "123456789");
        map.put("name", "Max Mustermann");
        map.put("email", "max.mustermann@xyz.com");
        map.put("picture", "picture1");

        GoogleOAuth2UserInfo oAuth2UserInfo = (GoogleOAuth2UserInfo) OAuth2UserInfoFactory
                .getOAuth2UserInfo(GOOGLE.name(), map);

        assertEquals("123456789", oAuth2UserInfo.getId());
        assertEquals("Max Mustermann", oAuth2UserInfo.getName());
        assertEquals("max.mustermann@xyz.com", oAuth2UserInfo.getEmail());
        assertEquals("picture1", oAuth2UserInfo.getImageUrl());
    }

    @Test
    void getOAuth2UserInfo_GITHUB() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "123456789");
        map.put("name", "Max Mustermann");
        map.put("avatar_url", "picture1");

        GitHubOAuth2UserInfo oAuth2UserInfo = (GitHubOAuth2UserInfo) OAuth2UserInfoFactory
                .getOAuth2UserInfo(GITHUB.name(), map);

        assertEquals("123456789", oAuth2UserInfo.getId());
        assertEquals("Max Mustermann", oAuth2UserInfo.getName());
        assertEquals("no-email", oAuth2UserInfo.getEmail());
        assertEquals("picture1", oAuth2UserInfo.getImageUrl());
    }

    @Test
    void getOAuth2UserInfo_unknown() {
        Map<String, Object> map = new HashMap<>();

        var exception = assertThrows(OAuth2AuthenticationProcessingException.class, () -> OAuth2UserInfoFactory
                .getOAuth2UserInfo("UNKNOWN", map));

        assertEquals("Sorry! Login with UNKNOWN is not supported yet.", exception.getMessage());
    }
}
