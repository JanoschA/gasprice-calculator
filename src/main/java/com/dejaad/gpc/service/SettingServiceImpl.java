package com.dejaad.gpc.service;

import com.dejaad.gpc.model.dto.SettingDto;
import com.dejaad.gpc.model.entity.UserSettingEntity;
import com.dejaad.gpc.model.mapper.UserSettingMapper;
import com.dejaad.gpc.repository.UserSettingRepository;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing user settings.
 * This class provides methods to update and retrieve user settings.
 */
@Service
public class SettingServiceImpl implements SettingService {

    private final UserSettingRepository userSettingRepository;

    /**
     * Constructs a new SettingServiceImpl with the specified UserSettingRepository.
     *
     * @param userSettingRepository the repository to use for accessing user settings
     */
    public SettingServiceImpl(UserSettingRepository userSettingRepository) {
        this.userSettingRepository = userSettingRepository;
    }

    /**
     * Updates the settings for the user with the specified ID.
     *
     * @param userId the ID of the user whose settings are to be updated
     * @param setting the new settings
     * @return the updated settings
     * @throws IllegalArgumentException if no user with the specified ID is found
     */
    @Override
    public SettingDto updateSetting(String userId, SettingDto setting) {
        UserSettingEntity userSettingEntity = getUserSettingEntity(userId);
        UserSettingMapper.mapSettingDtoToEntity(userSettingEntity, setting);
        userSettingRepository.save(userSettingEntity);
        return UserSettingMapper.mapUserSettingToDto(userSettingEntity);
    }

    /**
     * Retrieves the settings for the user with the specified ID.
     *
     * @param userId the ID of the user whose settings are to be retrieved
     * @return the settings of the user
     * @throws IllegalArgumentException if no user with the specified ID is found
     */
    @Override
    public SettingDto getSetting(String userId) {
        UserSettingEntity userSettingEntity = getUserSettingEntity(userId);
        return UserSettingMapper.mapUserSettingToDto(userSettingEntity);
    }

    /**
     * Retrieves the UserSettingEntity for the user with the specified ID.
     *
     * @param userId the ID of the user
     * @return the UserSettingEntity of the user
     * @throws IllegalArgumentException if no user with the specified ID is found
     */
    private UserSettingEntity getUserSettingEntity(String userId) {
        return userSettingRepository.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("Failed to find settings for user with id " + userId));
    }
}