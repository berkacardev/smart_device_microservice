package com.berkacar.smart_device_microservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryDataCreateRequest {
    private double weight;
    private double distance;
    private double gas;
    private double humidity;
    private double temperature;
    private double percentFullnessByDistance;
    private double percentFullnessByWeight;
}
