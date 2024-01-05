package com.dejaad.gpc.controller;

import com.dejaad.gpc.model.dto.SettingDto;
import com.dejaad.gpc.service.SettingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SettingControllerTest {

    @InjectMocks
    SettingController settingController;

    @Mock
    SettingService settingService;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(settingController).build();
    }

    @Test
    void testGetSetting() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("1");

        SettingDto settingDto = new SettingDto(100L);
        when(settingService.getSetting(mockPrincipal.getName())).thenReturn(settingDto);

        mockMvc.perform(get("/api/v1/setting")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateSetting() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("1");

        SettingDto settingDto = new SettingDto(100L);
        when(settingService.updateSetting(mockPrincipal.getName(), settingDto)).thenReturn(settingDto);

        mockMvc.perform(put("/api/v1/setting")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }
}