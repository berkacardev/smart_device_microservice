package com.berkacar.smart_device_microservice.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartDeviceFlag {
    private String deviceId;
    private boolean giveWarningSound;
    private boolean runGrinder;

}
