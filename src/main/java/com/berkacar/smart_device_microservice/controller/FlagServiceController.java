package com.berkacar.smart_device_microservice.controller;

import com.berkacar.smart_device_microservice.enums.Language;
import com.berkacar.smart_device_microservice.exception.enums.FriendlyMessageCodes;
import com.berkacar.smart_device_microservice.exception.utils.FriendlyMessageUtils;
import com.berkacar.smart_device_microservice.repository.entity.TelemetryData;
import com.berkacar.smart_device_microservice.response.FlagServiceResponse;
import com.berkacar.smart_device_microservice.response.FriendlyMessage;
import com.berkacar.smart_device_microservice.response.InternalApiResponse;
import com.berkacar.smart_device_microservice.response.TelemetryDataResponse;
import com.berkacar.smart_device_microservice.service.FlagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/flag/")
public class FlagServiceController {
    private final FlagService flagService;

    @Autowired
    public FlagServiceController(FlagService flagService) {
        this.flagService = flagService;
    }

    @GetMapping("/{smartDeviceId}")
    InternalApiResponse<FlagServiceResponse> startFlagService(
            @PathVariable String smartDeviceId,
            @RequestParam(value = "language", required = false, defaultValue = "EN") Language language
    ) {
        log.info("Starting flag service for device id : {}.", smartDeviceId);
        flagService.startFlagService(smartDeviceId);
        FlagServiceResponse response = new FlagServiceResponse(FriendlyMessageCodes.FLAG_SERVICE_SUCCESSFULLY_STARTED);
        return InternalApiResponse.<FlagServiceResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.FLAG_SERVICE_SUCCESSFULLY_STARTED))
                        .code(FriendlyMessageCodes.FLAG_SERVICE_SUCCESSFULLY_STARTED.getFriendlyMessageCode())
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();
    }
}
