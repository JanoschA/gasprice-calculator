package com.dejaad.gpc.controller;

import com.dejaad.gpc.model.dto.SettingDto;
import com.dejaad.gpc.security.CurrentUser;
import com.dejaad.gpc.security.UserPrincipal;
import com.dejaad.gpc.service.SettingService;
import org.springframework.web.bind.annotation.*;

// TODO: jUnit

@RestController
@RequestMapping("api/v1/setting")
public class SettingController {

    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping
    public SettingDto getSetting(@CurrentUser UserPrincipal userPrincipal) {
        return settingService.getSetting(userPrincipal.getId());
    }

    @PutMapping
    public SettingDto updateSetting(@CurrentUser UserPrincipal userPrincipal, @RequestBody SettingDto settingsDto) {
        return settingService.updateSetting(userPrincipal.getId(), settingsDto);
    }





}
