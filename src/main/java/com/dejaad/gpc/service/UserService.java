package com.dejaad.gpc.service;

import com.dejaad.gpc.model.oauth.User;
import com.dejaad.gpc.exception.ResourceNotFoundException;
import com.dejaad.gpc.security.oauth2.user.OAuth2UserInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

public interface UserService {
    UserDetails loadUserById(String id) throws ResourceNotFoundException;
    User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo);
    User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo);
}
