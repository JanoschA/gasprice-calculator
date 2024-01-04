package com.dejaad.gpc.model.mapper;

import com.dejaad.gpc.model.entity.UserSettingEntity;
import com.dejaad.gpc.model.oauth.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserSettingMapperTest {

    @ParameterizedTest
    @MethodSource("provideEntities")
    void mapUserSettingToDtoTest(UserSettingEntity entity) {
        System.out.println("entity = " + entity);
        var dto = UserSettingMapper.mapUserSettingToDto(entity);
        assertEquals(entity.getMaxGasTankVolume(), dto.maxGasTankVolume());
    }

    private static Stream<UserSettingEntity> provideEntities() {
        return Stream.of(
                new UserSettingEntity(UUID.randomUUID(), null, 0d),
                new UserSettingEntity(UUID.randomUUID(), null, 0.0d),
                new UserSettingEntity(UUID.randomUUID(), null, 0.01d),
                new UserSettingEntity(UUID.randomUUID(), null, 10d),
                new UserSettingEntity(UUID.randomUUID(), null, 10.0d),
                new UserSettingEntity(UUID.randomUUID(), null, 10.01d),
                new UserSettingEntity(UUID.randomUUID(), new User(), 0d),
                new UserSettingEntity(UUID.randomUUID(), new User(), 0.0d),
                new UserSettingEntity(UUID.randomUUID(), new User(), 0.01d),
                new UserSettingEntity(UUID.randomUUID(), new User(), 10d),
                new UserSettingEntity(UUID.randomUUID(), new User(), 10.0d),
                new UserSettingEntity(UUID.randomUUID(), new User(), 10.01d)
        );
    }

}
