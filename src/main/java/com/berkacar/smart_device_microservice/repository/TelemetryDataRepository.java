package com.berkacar.smart_device_microservice.repository;

import com.berkacar.smart_device_microservice.repository.entity.TelemetryData;

import java.util.Optional;

public interface TelemetryDataRepository {
    public Optional<TelemetryData> add(TelemetryData telemetryData);
}
