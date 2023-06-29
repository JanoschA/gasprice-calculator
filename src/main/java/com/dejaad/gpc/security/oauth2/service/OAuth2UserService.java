package com.dejaad.gpc.security.oauth2.service;

import com.dejaad.gpc.domain.oauth.AuthProvider;
import com.dejaad.gpc.domain.oauth.User;
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

@Service
public class OAuth2UserService {

    private final UserRepository userRepository;
    private final UserService userService;

    public OAuth2UserService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        Optional<User> userOptional = userRepository.findByProviderIdAndProvider(oAuth2UserInfo.getId(), AuthProvider.GITHUB);

        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
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
