package com.dejaad.gpc.security.oauth2.user;


import com.dejaad.gpc.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

import static com.dejaad.gpc.domain.oauth.AuthProvider.google;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
