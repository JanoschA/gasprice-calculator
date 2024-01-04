package com.dejaad.gpc.security.oauth2.service;

import com.dejaad.gpc.model.oauth.AuthProvider;
import com.dejaad.gpc.model.oauth.User;
import com.dejaad.gpc.exception.OAuth2AuthenticationProcessingException;
import com.dejaad.gpc.repository.UserRepository;
import com.dejaad.gpc.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OAuth2UserServiceTest {

    private final UserRepository userRepository = mock();
    private final UserService userService = mock();

    private final OAuth2UserService service = new OAuth2UserService(userRepository, userService);

    @Test
    void process_register_new_user() {
        OAuth2UserRequest oAuth2UserRequest = mock();
        OAuth2User oAuth2User = mock();
        ClientRegistration clientRegistration = mock();
        User user = mock();

        when(oAuth2UserRequest.getClientRegistration()).thenReturn(clientRegistration);
        when(clientRegistration.getRegistrationId()).thenReturn("GOOGLE");

        when(userService.registerNewUser(any(), any())).thenReturn(user);

        service.process(oAuth2UserRequest, oAuth2User);

        verify(userService, times(1)).registerNewUser(any(), any());
        verify(userRepository, times(1)).findByProviderIdAndProvider(any(), any());
    }

    @Test
    void process_update_user() {
        OAuth2UserRequest oAuth2UserRequest = mock();
        OAuth2User oAuth2User = mock();
        ClientRegistration clientRegistration = mock();
        User user = mock();

        when(oAuth2UserRequest.getClientRegistration()).thenReturn(clientRegistration);
        when(clientRegistration.getRegistrationId()).thenReturn("GOOGLE");

        when(userRepository.findByProviderIdAndProvider(any(), any())).thenReturn(Optional.of(user));
        when(user.getProvider()).thenReturn(AuthProvider.GOOGLE);

        when(userService.updateExistingUser(any(), any())).thenReturn(user);

        service.process(oAuth2UserRequest, oAuth2User);

        verify(userService, times(1)).updateExistingUser(any(), any());
        verify(userRepository, times(1)).findByProviderIdAndProvider(any(), any());
    }

    @Test
    void process_update_user_wrong_provider_id() {
        OAuth2UserRequest oAuth2UserRequest = mock();
        OAuth2User oAuth2User = mock();
        ClientRegistration clientRegistration = mock();
        User user = mock();

        when(oAuth2UserRequest.getClientRegistration()).thenReturn(clientRegistration);
        when(clientRegistration.getRegistrationId()).thenReturn("GOOGLE");

        when(userRepository.findByProviderIdAndProvider(any(), any())).thenReturn(Optional.of(user));
        when(user.getProvider()).thenReturn(AuthProvider.GITHUB);

        var exception = assertThrows(OAuth2AuthenticationProcessingException.class, () -> service.process(oAuth2UserRequest, oAuth2User));

        assertEquals("Looks like you're signed up with GITHUB account. Please use your GITHUB account to login.", exception.getMessage());
        verify(userRepository, times(1)).findByProviderIdAndProvider(any(), any());
    }
}
