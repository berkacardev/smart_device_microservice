package com.berkacar.smart_device_microservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlagRequest {
    private String sender;
    private String receiverId;
    private boolean giveWarningSound;
    private boolean runGrinder;
}
