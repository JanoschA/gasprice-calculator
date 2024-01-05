package com.dejaad.gpc.model.mapper;

import com.dejaad.gpc.model.dto.SettingDto;
import com.dejaad.gpc.model.entity.UserSettingEntity;
import com.dejaad.gpc.model.oauth.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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

    @ParameterizedTest
    @MethodSource("provideDtos")
    void mapSettingDtoToEntityTest(SettingDto dto, UserSettingEntity expectedEntity) {
        UserSettingMapper.mapSettingDtoToEntity(expectedEntity, dto);
        assertEquals(expectedEntity.getMaxGasTankVolume(), dto.maxGasTankVolume());
    }

    private static Stream<Arguments> provideDtos() {
        return Stream.of(
                Arguments.of(new SettingDto(0d), new UserSettingEntity(null, null, 200d)),
                Arguments.of(new SettingDto(0.0d), new UserSettingEntity(null, null, 200d)),
                Arguments.of(new SettingDto(0.01d), new UserSettingEntity(null, null, 200d)),
                Arguments.of(new SettingDto(10d), new UserSettingEntity(null, null, 200d)),
                Arguments.of(new SettingDto(10.0d), new UserSettingEntity(null, null, 200d)),
                Arguments.of(new SettingDto(10.01d), new UserSettingEntity(null, null, 200d)),
                Arguments.of(new SettingDto(0d), new UserSettingEntity(null, null, 0d)),
                Arguments.of(new SettingDto(0.0d), new UserSettingEntity(null, null, 0.0d)),
                Arguments.of(new SettingDto(0.01d), new UserSettingEntity(null, null, 0.01d)),
                Arguments.of(new SettingDto(10d), new UserSettingEntity(null, null, 10d)),
                Arguments.of(new SettingDto(10.0d), new UserSettingEntity(null, null, 10.0d)),
                Arguments.of(new SettingDto(10.01d), new UserSettingEntity(null, null, 10.01d))
        );
    }

}
