package com.berkacar.smart_device_microservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@Getter
@Setter
@ConfigurationProperties(prefix = "gcp.firebase")
public class FirebaseProperties {
    private Resource resource;
}
