package com.berkacar.smart_device_microservice.controller;

import com.berkacar.smart_device_microservice.enums.Language;
import com.berkacar.smart_device_microservice.exception.enums.FriendlyMessageCodes;
import com.berkacar.smart_device_microservice.exception.utils.FriendlyMessageUtils;
import com.berkacar.smart_device_microservice.repository.entity.TelemetryData;
import com.berkacar.smart_device_microservice.request.TelemetryDataCreateRequest;
import com.berkacar.smart_device_microservice.response.FriendlyMessage;
import com.berkacar.smart_device_microservice.response.InternalApiResponse;
import com.berkacar.smart_device_microservice.response.TelemetryDataResponse;
import com.berkacar.smart_device_microservice.service.TelemetryDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/smart-device/")
public class TelemetryDataController {
    private final TelemetryDataService telemetryDataService;

    @Autowired
    public TelemetryDataController(TelemetryDataService telemetryDataService) {
        this.telemetryDataService = telemetryDataService;
    }

    @PostMapping("/{smartDeviceId}/telemetry-data")
    InternalApiResponse<TelemetryDataResponse> sendTelemetryData(
            @PathVariable String smartDeviceId,
            @RequestBody TelemetryDataCreateRequest telemetryDataCreateRequest,
            @RequestParam(value = "language", required = false, defaultValue = "EN") Language language
    ) {
        log.info("Sending telemetry data to smart device {}.", smartDeviceId + telemetryDataCreateRequest.toString());
        TelemetryData telemetryData = TelemetryData
                .builder()
                .deviceId(smartDeviceId)
                .weight(telemetryDataCreateRequest.getWeight())
                .distance(telemetryDataCreateRequest.getDistance())
                .gas(telemetryDataCreateRequest.getGas())
                .humidity(telemetryDataCreateRequest.getHumidity())
                .temperature(telemetryDataCreateRequest.getTemperature())
                .percentFullnessByDistance(telemetryDataCreateRequest.getPercentFullnessByDistance())
                .percentFullnessByWeight(telemetryDataCreateRequest.getPercentFullnessByWeight())
                .build();
        TelemetryDataResponse response = telemetryDataService.sendTelemetryData(telemetryData);
        return InternalApiResponse.<TelemetryDataResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.TELEMETRY_DATA_IS_SUCCESSFULLY_ADDED))
                        .code(FriendlyMessageCodes.TELEMETRY_DATA_IS_SUCCESSFULLY_ADDED.getFriendlyMessageCode())
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();

    }
}
