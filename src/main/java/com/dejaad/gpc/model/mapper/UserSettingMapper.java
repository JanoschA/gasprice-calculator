package com.dejaad.gpc.model.mapper;

import com.dejaad.gpc.model.dto.SettingDto;
import com.dejaad.gpc.model.entity.UserSettingEntity;

public class UserSettingMapper {

    public static SettingDto mapUserSettingToDto(UserSettingEntity entity) {
        return new SettingDto(entity.getMaxGasTankVolume());
    }

    public static void mapSettingDtoToEntity(UserSettingEntity entity, SettingDto dto) {
        entity.setMaxGasTankVolume(dto.maxGasTankVolume());
    }

}
