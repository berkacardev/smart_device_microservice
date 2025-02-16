package com.berkacar.smart_device_microservice.exception.enums;

public enum FriendlyMessageCodes implements FriendlyMessageCode{

    OK(1000),
    ERROR(1001),
    SUCCESS(1002),
    ILLEGAL_OPERATION_EXCEPTION(1004),
    NOT_CATEGORIZED_EXCEPTION(1005),
    TELEMETRY_DATA_IS_NOT_ADDED_EXCEPTION(2001),
    TELEMETRY_DATA_IS_SUCCESSFULLY_ADDED(2002),
    FLAG_SERVICE_SUCCESSFULLY_STARTED(3001),
    FLAG_SERVICE_STARTING_ERROR(3002);

    private final int value;

    FriendlyMessageCodes(int value) {
        this.value = value;
    }

    @Override
    public int getFriendlyMessageCode() {
        return this.value;
    }
}
