package com.berkacar.smart_device_microservice.repository;

import com.berkacar.smart_device_microservice.repository.entity.SmartDeviceFlag;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;

public interface FlagDataRepository<T> {
    void listenToFlags(String deviceId, EventListener<T> listener);
    SmartDeviceFlag updateFlag(SmartDeviceFlag flag);
}
