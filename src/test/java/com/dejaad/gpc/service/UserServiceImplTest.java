package com.dejaad.gpc.service;

import com.dejaad.gpc.APIntegrationTest;
import com.dejaad.gpc.model.oauth.AuthProvider;
import com.dejaad.gpc.model.oauth.User;
import com.dejaad.gpc.exception.ResourceNotFoundException;
import com.dejaad.gpc.repository.UserRepository;
import com.dejaad.gpc.security.UserPrincipal;
import com.dejaad.gpc.security.oauth2.user.OAuth2UserInfo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@APIntegrationTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static String USER_ID;

    @Test
    void loadUserById() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);

        UserPrincipal resultUser = (UserPrincipal) userService.loadUserById(user.getId());

        assertEquals(user.getId(), resultUser.getId());
    }

    @Test
    void loadUserById_not_found() {
        assertThrows(ResourceNotFoundException.class, () -> userService.loadUserById("1"));
    }

    @Test
    @Order(1)
    void registerNewUser() {
        OAuth2UserRequest oAuth2UserRequest = mock();
        OAuth2UserInfo oAuth2UserInfo = mock();
        ClientRegistration clientRegistration = mock();

        when(oAuth2UserRequest.getClientRegistration()).thenReturn(clientRegistration);
        when(clientRegistration.getRegistrationId()).thenReturn("GOOGLE");
        when(oAuth2UserInfo.getId()).thenReturn("123");
        when(oAuth2UserInfo.getName()).thenReturn("Max Mustermann");
        when(oAuth2UserInfo.getEmail()).thenReturn("no-email");
        when(oAuth2UserInfo.getImageUrl()).thenReturn("picture1");

        User user = userService.registerNewUser(oAuth2UserRequest, oAuth2UserInfo);

        assertNotNull(user.getId());
        USER_ID = user.getId();
        assertEquals("Max Mustermann", user.getName());
        assertEquals("no-email", user.getEmail());
        assertEquals("picture1", user.getImageUrl());
        assertEquals("123", user.getProviderId());
        assertEquals(AuthProvider.GOOGLE, user.getProvider());
    }

    @Test
    @Order(2)
    void updateExistingUser() {
        OAuth2UserInfo oAuth2UserInfo = mock();
        User user = userRepository.findById(USER_ID).get();

        when(oAuth2UserInfo.getImageUrl()).thenReturn("picture123456");
        when(oAuth2UserInfo.getName()).thenReturn("Max2 Mustermann2");

        User updatedUser = userService.updateExistingUser(user, oAuth2UserInfo);

        assertEquals("Max2 Mustermann2", updatedUser.getName());
        assertEquals("picture123456", user.getImageUrl());
    }
}
