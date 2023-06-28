package com.dejaad.gpc.security.oauth2.user;


import com.dejaad.gpc.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

import static com.dejaad.gpc.domain.oauth.AuthProvider.GITHUB;
import static com.dejaad.gpc.domain.oauth.AuthProvider.GOOGLE;

public class OAuth2UserInfoFactory {

    private OAuth2UserInfoFactory() throws IllegalAccessException {
        throw new IllegalAccessException("UTILITY CLASS!");
    }

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        }
        else if (registrationId.equalsIgnoreCase(GITHUB.toString())) {
            return new GitHubOAuth2UserInfo(attributes);
        }
        else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
