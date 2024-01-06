package com.dejaad.gpc.security.oauth2.service;

import com.dejaad.gpc.model.oauth.AuthProvider;
import com.dejaad.gpc.model.oauth.User;
import com.dejaad.gpc.exception.OAuth2AuthenticationProcessingException;
import com.dejaad.gpc.repository.UserRepository;
import com.dejaad.gpc.security.UserPrincipal;
import com.dejaad.gpc.security.oauth2.user.OAuth2UserInfo;
import com.dejaad.gpc.security.oauth2.user.OAuth2UserInfoFactory;
import com.dejaad.gpc.service.UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling OAuth2 user information.
 * This service is responsible for processing OAuth2 user requests and either updating an existing user or registering a new one.
 */
@Service
public class OAuth2UserService {

    private final UserRepository userRepository;
    private final UserService userService;

    public OAuth2UserService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Processes the OAuth2UserRequest and OAuth2User to either update an existing user or register a new one.
     *
     * @param oAuth2UserRequest The OAuth2UserRequest object containing details of the OAuth2 request.
     * @param oAuth2User The OAuth2User object containing details of the authenticated user.
     * @return A UserPrincipal object representing the authenticated user.
     * @throws OAuth2AuthenticationProcessingException If the provider of the authenticated user does not match the provider of the existing user.
     */
    public OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        AuthProvider provider = AuthProvider.valueOf(
                oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());

        Optional<User> userOptional = userRepository.findByProviderIdAndProviderNot(oAuth2UserInfo.getId(), provider);

        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(provider)) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = userService.updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = userService.registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

}
