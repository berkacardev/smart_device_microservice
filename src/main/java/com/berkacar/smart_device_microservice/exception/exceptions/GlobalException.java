package com.berkacar.smart_device_microservice.exception.exceptions;

import com.berkacar.smart_device_microservice.enums.Language;
import com.berkacar.smart_device_microservice.exception.enums.FriendlyMessageCode;
import com.berkacar.smart_device_microservice.exception.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class GlobalException extends RuntimeException{
    private Language language;
    private FriendlyMessageCode friendlyMessageCode;

    public GlobalException() {

    }

    public GlobalException(Language language, FriendlyMessageCode friendlyMessageCode, String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[{} -> message: {} developer message: {}]", this.getClass().getSimpleName(), FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode), message);
    }
}
