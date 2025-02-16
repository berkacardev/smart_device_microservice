package com.berkacar.smart_device_microservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlagResponse {
    private String sender;
    private String receiverId;
    private boolean giveWarningSound;
    private boolean runGrinder;
}
