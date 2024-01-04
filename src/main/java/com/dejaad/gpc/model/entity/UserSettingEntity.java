package com.dejaad.gpc.model.entity;

import com.dejaad.gpc.model.oauth.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_setting")
public class UserSettingEntity {

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "max_gas_tank_volume")
    private double maxGasTankVolume;
}
