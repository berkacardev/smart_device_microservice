package com.berkacar.smart_device_microservice.response;

import com.berkacar.smart_device_microservice.exception.enums.FriendlyMessageCodes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlagServiceResponse {
    private FriendlyMessageCodes statusCode;
}
