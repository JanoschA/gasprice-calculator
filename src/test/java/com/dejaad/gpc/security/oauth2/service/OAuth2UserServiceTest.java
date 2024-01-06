package com.dejaad.gpc.security.oauth2.service;

import com.dejaad.gpc.model.oauth.AuthProvider;
import com.dejaad.gpc.model.oauth.User;
import com.dejaad.gpc.exception.OAuth2AuthenticationProcessingException;
import com.dejaad.gpc.repository.UserRepository;
import com.dejaad.gpc.service.UserService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OAuth2UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = mock(UserService.class);

    private final OAuth2UserService service = new OAuth2UserService(userRepository, userService);

    @ParameterizedTest
    @ValueSource(strings = {"GOOGLE", "google", "GITHUB", "github"})
    void process_register_new_user(String registrationId) {
        OAuth2UserRequest oAuth2UserRequest = mock(OAuth2UserRequest.class);
        OAuth2User oAuth2User = mock(OAuth2User.class);
        ClientRegistration clientRegistration = mock(ClientRegistration.class);
        User user = mock(User.class);

        when(oAuth2UserRequest.getClientRegistration()).thenReturn(clientRegistration);
        when(clientRegistration.getRegistrationId()).thenReturn(registrationId);

        when(userService.registerNewUser(any(), any())).thenReturn(user);

        service.process(oAuth2UserRequest, oAuth2User);

        verify(userService, times(1)).registerNewUser(any(), any());
        verify(userRepository, times(1)).findByProviderIdAndProviderNot(any(), any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"GOOGLE", "google", "GITHUB", "github"})
    void process_update_user(String registrationId) {
        OAuth2UserRequest oAuth2UserRequest = mock(OAuth2UserRequest.class);
        OAuth2User oAuth2User = mock(OAuth2User.class);
        ClientRegistration clientRegistration = mock(ClientRegistration.class);
        User user = mock(User.class);

        when(oAuth2UserRequest.getClientRegistration()).thenReturn(clientRegistration);
        when(clientRegistration.getRegistrationId()).thenReturn(registrationId);

        when(userRepository.findByProviderIdAndProviderNot(any(), any())).thenReturn(Optional.of(user));
        when(user.getProvider()).thenReturn(AuthProvider.valueOf(registrationId.toUpperCase()));

        when(userService.updateExistingUser(any(), any())).thenReturn(user);

        service.process(oAuth2UserRequest, oAuth2User);

        verify(userService, times(1)).updateExistingUser(any(), any());
        verify(userRepository, times(1)).findByProviderIdAndProviderNot(any(), any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"GOOGLE", "google"})
    void process_update_user_wrong_provider_id(String registrationId) {
        OAuth2UserRequest oAuth2UserRequest = mock(OAuth2UserRequest.class);
        OAuth2User oAuth2User = mock(OAuth2User.class);
        ClientRegistration clientRegistration = mock(ClientRegistration.class);
        User user = mock(User.class);

        when(oAuth2UserRequest.getClientRegistration()).thenReturn(clientRegistration);
        when(clientRegistration.getRegistrationId()).thenReturn(registrationId);

        when(userRepository.findByProviderIdAndProviderNot(any(), any())).thenReturn(Optional.of(user));
        when(user.getProvider()).thenReturn(AuthProvider.GITHUB);

        var exception = assertThrows(OAuth2AuthenticationProcessingException.class, () -> service.process(oAuth2UserRequest, oAuth2User));

        assertEquals("Looks like you're signed up with GITHUB account. Please use your GITHUB account to login.", exception.getMessage());
        verify(userRepository, times(1)).findByProviderIdAndProviderNot(any(), any());
    }
}