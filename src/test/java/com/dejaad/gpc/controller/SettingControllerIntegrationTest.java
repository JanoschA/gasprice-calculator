package com.dejaad.gpc.controller;

import com.dejaad.gpc.model.dto.SettingDto;
import com.dejaad.gpc.model.oauth.User;
import com.dejaad.gpc.repository.UserRepository;
import com.dejaad.gpc.security.TokenProvider;
import com.dejaad.gpc.security.util.CookieUtils;
import com.dejaad.gpc.service.SettingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SettingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SettingService settingService;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        when(tokenProvider.validateToken(any())).thenReturn(true);
        when(tokenProvider.getUserIdFromToken(any())).thenReturn("1");
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
    }

    @Test
    @WithMockUser
    public void shouldReturnSettingWhenAuthenticated() throws Exception {
        SettingDto settingDto = new SettingDto(100L);
        when(settingService.getSetting(any())).thenReturn(settingDto);

        mockMvc.perform(get("/api/v1/setting")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .cookie(new Cookie(CookieUtils.getTokenCookieName(), "mockOAuth2Token")))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(settingDto)));
    }

    @Test
    public void shouldReturnUnauthorizedWhenNotAuthenticated() throws Exception {
        SettingDto settingDto = new SettingDto(100L);
        when(settingService.getSetting(any())).thenReturn(settingDto);

        mockMvc.perform(get("/api/v1/setting"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void shouldUpdateSettingWhenAuthenticated() throws Exception {
        SettingDto newSettingDto = new SettingDto(200L);
        when(settingService.updateSetting(any(), eq(newSettingDto))).thenReturn(newSettingDto);

        mockMvc.perform(put("/api/v1/setting")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .cookie(new Cookie(CookieUtils.getTokenCookieName(), "mockOAuth2Token"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSettingDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(newSettingDto)));
    }

    @Test
    public void shouldReturnForbiddenWhenUpdatingSettingUnauthenticated() throws Exception {
        SettingDto newSettingDto = new SettingDto(200L);
        when(settingService.updateSetting(any(), eq(newSettingDto))).thenReturn(newSettingDto);

        mockMvc.perform(put("/api/v1/setting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSettingDto)))
                .andExpect(status().isForbidden());
    }
}