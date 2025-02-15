package com.berkacar.smart_device_microservice.exception.handler;

import com.berkacar.smart_device_microservice.exception.enums.FriendlyMessageCodes;
import com.berkacar.smart_device_microservice.exception.exceptions.GlobalException;
import com.berkacar.smart_device_microservice.exception.exceptions.IllegalOperationException;
import com.berkacar.smart_device_microservice.exception.exceptions.TelemetryDataIsNotAddedException;
import com.berkacar.smart_device_microservice.exception.utils.FriendlyMessageUtils;
import com.berkacar.smart_device_microservice.response.FriendlyMessage;
import com.berkacar.smart_device_microservice.response.InternalApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalOperationException.class)
    public InternalApiResponse<String> handleIllegalOperationException(IllegalOperationException exception) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ILLEGAL_OPERATION_EXCEPTION))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .code(FriendlyMessageCodes.ILLEGAL_OPERATION_EXCEPTION.getFriendlyMessageCode())
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GlobalException.class)
    public InternalApiResponse<String> handleGlobalException(GlobalException exception) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.NOT_CATEGORIZED_EXCEPTION))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .code(FriendlyMessageCodes.NOT_CATEGORIZED_EXCEPTION.getFriendlyMessageCode())
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TelemetryDataIsNotAddedException.class)
    public InternalApiResponse<String> handleTelemetryDataIsNotAddedException(TelemetryDataIsNotAddedException exception) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.TELEMETRY_DATA_IS_NOT_ADDED_EXCEPTION))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .code(FriendlyMessageCodes.TELEMETRY_DATA_IS_NOT_ADDED_EXCEPTION.getFriendlyMessageCode())
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();

    }
}
