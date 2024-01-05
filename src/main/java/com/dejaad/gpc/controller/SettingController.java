package com.dejaad.gpc.controller;

import com.dejaad.gpc.model.dto.SettingDto;
import com.dejaad.gpc.security.CurrentUser;
import com.dejaad.gpc.security.UserPrincipal;
import com.dejaad.gpc.service.SettingService;
import org.springframework.web.bind.annotation.*;

/**
 * SettingController is a REST controller that handles HTTP requests related to user settings.
 * It provides endpoints for getting and updating user settings.
 */
@RestController
@RequestMapping("api/v1/setting")
public class SettingController {

    private final SettingService settingService;

    /**
     * Constructs a new SettingController with the specified SettingService.
     *
     * @param settingService the service to handle setting-related operations
     */
    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    /**
     * Handles the HTTP GET request for getting user settings.
     *
     * @param userPrincipal the current user
     * @return the settings of the current user
     */
    @GetMapping
    public SettingDto getSetting(@CurrentUser UserPrincipal userPrincipal) {
        return settingService.getSetting(userPrincipal.getId());
    }

    /**
     * Handles the HTTP PUT request for updating user settings.
     *
     * @param userPrincipal the current user
     * @param settingsDto the new settings to be updated
     * @return the updated settings of the current user
     */
    @PutMapping
    public SettingDto updateSetting(@CurrentUser UserPrincipal userPrincipal, @RequestBody SettingDto settingsDto) {
        return settingService.updateSetting(userPrincipal.getId(), settingsDto);
    }
}