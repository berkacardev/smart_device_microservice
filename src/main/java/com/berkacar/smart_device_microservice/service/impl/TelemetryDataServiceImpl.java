package com.berkacar.smart_device_microservice.service.impl;

import com.berkacar.smart_device_microservice.enums.Language;
import com.berkacar.smart_device_microservice.exception.enums.FriendlyMessageCodes;
import com.berkacar.smart_device_microservice.exception.exceptions.TelemetryDataIsNotAddedException;
import com.berkacar.smart_device_microservice.repository.TelemetryDataRepository;
import com.berkacar.smart_device_microservice.repository.entity.TelemetryData;
import com.berkacar.smart_device_microservice.response.TelemetryDataResponse;
import com.berkacar.smart_device_microservice.service.TelemetryDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TelemetryDataServiceImpl implements TelemetryDataService {

    private final TelemetryDataRepository telemetryDataRepository;


    @Autowired
    public TelemetryDataServiceImpl(TelemetryDataRepository telemetryDataRepository) {
        this.telemetryDataRepository = telemetryDataRepository;
    }

    @Override
    public TelemetryDataResponse sendTelemetryData(TelemetryData telemetryData) {
        var result = telemetryDataRepository.add(telemetryData);
        if (result.isEmpty()) {
            throw new TelemetryDataIsNotAddedException(Language.EN, FriendlyMessageCodes.TELEMETRY_DATA_IS_SUCCESSFULLY_ADDED, FriendlyMessageCodes.TELEMETRY_DATA_IS_SUCCESSFULLY_ADDED.toString());
        } else {
            return convertTelemetryData(result.get());
        }
    }

    private TelemetryDataResponse convertTelemetryData(TelemetryData telemetryData) {
        return TelemetryDataResponse
                .builder()
                .weight(telemetryData.getWeight())
                .distance(telemetryData.getDistance())
                .gas(telemetryData.getGas())
                .humidity(telemetryData.getHumidity())
                .temperature(telemetryData.getTemperature())
                .percentFullnessByDistance(telemetryData.getPercentFullnessByDistance())
                .percentFullnessByWeight(telemetryData.getPercentFullnessByWeight())
                .dataTime(telemetryData.getDataTime())
                .build();
    }
}
