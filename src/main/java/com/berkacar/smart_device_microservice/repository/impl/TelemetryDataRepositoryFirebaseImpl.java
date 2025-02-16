package com.berkacar.smart_device_microservice.repository.impl;

import com.berkacar.smart_device_microservice.repository.TelemetryDataRepository;
import com.berkacar.smart_device_microservice.repository.entity.TelemetryData;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TelemetryDataRepositoryFirebaseImpl implements TelemetryDataRepository {

    private final Firestore firestore;

    @Autowired
    public TelemetryDataRepositoryFirebaseImpl(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Optional<TelemetryData> add(TelemetryData telemetryData) {
        try {
            Timestamp savedDateTime = Timestamp.now();

            Map<String, Object> telemetryDataMap = new HashMap<>();
            telemetryDataMap.put("distance", telemetryData.getDistance());
            telemetryDataMap.put("gas", telemetryData.getGas());
            telemetryDataMap.put("humidity", telemetryData.getHumidity());
            telemetryDataMap.put("percent_fullness_by_distance", telemetryData.getPercentFullnessByDistance());
            telemetryDataMap.put("percent_fullness_by_weight", telemetryData.getPercentFullnessByWeight());
            telemetryDataMap.put("temperature", telemetryData.getTemperature());
            telemetryDataMap.put("weight", telemetryData.getWeight());
            telemetryDataMap.put("data_time", savedDateTime);

            WriteResult docRef = firestore
                    .collection("smart_devices")
                    .document(telemetryData.getDeviceId())
                    .collection("telemetry_data")
                    .document(String.valueOf(System.currentTimeMillis()))
                    .set(telemetryDataMap)
                    .get();
            telemetryData.setDataTime(LocalDateTime.now());
            return Optional.of(telemetryData);

        } catch (Exception e) {
            return Optional.empty();
        }

    }
}
