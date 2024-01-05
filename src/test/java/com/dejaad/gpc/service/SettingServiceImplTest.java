package com.dejaad.gpc.service;

import com.dejaad.gpc.model.dto.SettingDto;
import com.dejaad.gpc.model.entity.UserSettingEntity;
import com.dejaad.gpc.repository.UserSettingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SettingServiceImplTest {

    private static final String TEST_USER_ID = "testUserId";
    private static final double MAX_GAS_TANK_VOLUME = 200L;

    @InjectMocks
    private SettingServiceImpl settingService;

    @Mock
    private UserSettingRepository userSettingRepository;

    private UserSettingEntity userSettingEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        userSettingEntity = new UserSettingEntity();
        userSettingEntity.setMaxGasTankVolume(MAX_GAS_TANK_VOLUME);

        when(userSettingRepository.findByUser_Id(TEST_USER_ID)).thenReturn(Optional.of(userSettingEntity));
    }

    @Test
    void shouldReturnUserSettingWhenGetSettingIsCalled() {
        SettingDto result = settingService.getSetting(TEST_USER_ID);

        assertEquals(MAX_GAS_TANK_VOLUME, result.maxGasTankVolume());
        verify(userSettingRepository).findByUser_Id(TEST_USER_ID);
    }

    @Test
    void shouldUpdateAndReturnNewSettingWhenUpdateSettingIsCalled() {
        SettingDto settingDto = new SettingDto(100L);

        when(userSettingRepository.save(any(UserSettingEntity.class))).thenReturn(userSettingEntity);

        SettingDto result = settingService.updateSetting(TEST_USER_ID, settingDto);

        assertEquals(settingDto.maxGasTankVolume(), result.maxGasTankVolume());
        verify(userSettingRepository).findByUser_Id(TEST_USER_ID);
        verify(userSettingRepository).save(any(UserSettingEntity.class));
    }
}