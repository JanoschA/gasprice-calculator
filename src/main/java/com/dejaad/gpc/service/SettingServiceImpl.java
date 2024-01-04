package com.dejaad.gpc.service;

import com.dejaad.gpc.model.dto.SettingDto;
import com.dejaad.gpc.model.entity.UserSettingEntity;
import com.dejaad.gpc.model.mapper.UserSettingMapper;
import com.dejaad.gpc.repository.UserSettingRepository;
import org.springframework.stereotype.Service;

// TODO: jUnit Test
@Service
public class SettingServiceImpl implements SettingService {

    private final UserSettingRepository userSettingRepository;

    public SettingServiceImpl(UserSettingRepository userSettingRepository) {
        this.userSettingRepository = userSettingRepository;
    }

    @Override
    public SettingDto updateSetting(String userId, SettingDto setting) {
        UserSettingEntity userSettingEntity = userSettingRepository.findByUser_Id(userId).orElseThrow();
        UserSettingMapper.mapSettingDtoToEntity(userSettingEntity, setting);
        userSettingRepository.save(userSettingEntity);
        return UserSettingMapper.mapUserSettingToDto(userSettingEntity);
    }

    @Override
    public SettingDto getSetting(String userId) {
        UserSettingEntity userSettingEntity = userSettingRepository.findByUser_Id(userId).orElseThrow();
        return UserSettingMapper.mapUserSettingToDto(userSettingEntity);
    }
}
