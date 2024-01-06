package com.dejaad.gpc.service;

import com.dejaad.gpc.model.oauth.AuthProvider;
import com.dejaad.gpc.model.oauth.User;
import com.dejaad.gpc.exception.ResourceNotFoundException;
import com.dejaad.gpc.repository.UserRepository;
import com.dejaad.gpc.security.UserPrincipal;
import com.dejaad.gpc.security.oauth2.user.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for handling User data.
 * This service provides methods for loading a user by ID, registering a new user, and updating an existing user.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Loads a User by ID.
     *
     * @param id The ID of the User to load.
     * @return A UserDetails object representing the loaded User.
     * @throws ResourceNotFoundException If no User with the given ID could be found.
     */
    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }

    /**
     * Registers a new User.
     *
     * @param oAuth2UserRequest The OAuth2UserRequest object containing details of the OAuth2 request.
     * @param oAuth2UserInfo The OAuth2UserInfo object containing details of the authenticated user.
     * @return The registered User.
     */
    public User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();

        user.setId(UUID.randomUUID().toString());
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(user);
    }

    /**
     * Updates an existing User.
     *
     * @param existingUser The existing User to update.
     * @param oAuth2UserInfo The OAuth2UserInfo object containing details of the authenticated user.
     * @return The updated User.
     */
    public User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }
}
