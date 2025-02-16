package com.berkacar.smart_device_microservice.service;

import com.berkacar.smart_device_microservice.repository.entity.TelemetryData;
import com.berkacar.smart_device_microservice.response.TelemetryDataResponse;

public interface TelemetryService {
    TelemetryDataResponse sendTelemetryData(TelemetryData telemetryData);
}
