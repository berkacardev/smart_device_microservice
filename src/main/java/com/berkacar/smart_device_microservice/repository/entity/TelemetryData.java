package com.berkacar.smart_device_microservice.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryData {
    private String deviceId;
    private double weight;
    private double distance;
    private double gas;
    private double humidity;
    private double temperature;
    private double percentFullnessByDistance;
    private double percentFullnessByWeight;
    private LocalDateTime dataTime;
}
