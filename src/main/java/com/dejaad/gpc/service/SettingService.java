package com.dejaad.gpc.service;

import com.dejaad.gpc.model.dto.SettingDto;

public interface SettingService {
    SettingDto updateSetting(String userId, SettingDto setting);

    SettingDto getSetting(String userId);
}
