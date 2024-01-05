package com.dejaad.gpc.model.mapper;

import com.dejaad.gpc.model.dto.SettingDto;
import com.dejaad.gpc.model.entity.UserSettingEntity;

/**
 * Utility class for mapping between UserSettingEntity and SettingDto.
 */
public class UserSettingMapper {

    private UserSettingMapper() throws IllegalAccessException {
        throw new IllegalAccessException("This is a utility class and cannot be instantiated");
    }

    /**
     * Maps a UserSettingEntity to a SettingDto.
     *
     * @param entity the UserSettingEntity to map
     * @return a SettingDto with the same properties as the given entity
     */
    public static SettingDto mapUserSettingToDto(UserSettingEntity entity) {
        return new SettingDto(entity.getMaxGasTankVolume());
    }

    /**
     * Maps a SettingDto to a UserSettingEntity.
     * This method modifies the given entity to have the same properties as the given dto.
     *
     * @param entity the UserSettingEntity to modify
     * @param dto the SettingDto to map
     */
    public static void mapSettingDtoToEntity(UserSettingEntity entity, SettingDto dto) {
        entity.setMaxGasTankVolume(dto.maxGasTankVolume());
    }
}
