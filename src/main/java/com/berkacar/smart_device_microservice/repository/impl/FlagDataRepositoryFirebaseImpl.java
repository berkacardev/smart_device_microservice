package com.berkacar.smart_device_microservice.repository.impl;

import com.berkacar.smart_device_microservice.repository.FlagDataRepository;
import com.berkacar.smart_device_microservice.repository.entity.SmartDeviceFlag;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class FlagDataRepositoryFirebaseImpl implements FlagDataRepository<DocumentSnapshot> {

    private final Firestore firestore;

    @Autowired
    public FlagDataRepositoryFirebaseImpl(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public void listenToFlags(String deviceId, EventListener<DocumentSnapshot> listener) {
        firestore.collection("smart_devices").document(deviceId).collection("flags").document("main_flag").addSnapshotListener(listener);
    }

    @Override
    public SmartDeviceFlag updateFlag(SmartDeviceFlag flag) {
        try {
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("give_warning_sound", flag.isGiveWarningSound());
            updateMap.put("run_grinder", flag.isRunGrinder());
            firestore.collection("smart_devices").document(flag.getDeviceId()).collection("flags").document("main_flag").set(updateMap);
            return flag;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
