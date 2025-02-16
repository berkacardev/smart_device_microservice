package com.berkacar.smart_device_microservice.service.impl;

import com.berkacar.smart_device_microservice.repository.FlagDataRepository;
import com.berkacar.smart_device_microservice.response.FlagResponse;
import com.berkacar.smart_device_microservice.service.FlagService;
import com.berkacar.smart_device_microservice.service.SocketService;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.FirestoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Slf4j
@Service
public class FlagServiceImpl implements FlagService {

    private final FlagDataRepository flagDataRepository;
    private final SocketService socketService;

    @Autowired
    public FlagServiceImpl(FlagDataRepository flagDataRepository, SocketService socketService) {
        this.flagDataRepository = flagDataRepository;
        this.socketService = socketService;
    }

    @Override
    public void startFlagService(String deviceId) {
        flagDataRepository.listenToFlags(deviceId, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirestoreException e) {
                if (e != null) {
                    log.error("Error listening to Firestore: {}", e.getMessage());
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    boolean giveWarningSound = (Boolean) documentSnapshot.getData().get("give_warning_sound");
                    boolean runGrinder = (Boolean) documentSnapshot.getData().get("run_grinder");
                    FlagResponse flagResponse = FlagResponse.builder()
                            .receiverId(deviceId)
                            .sender("server")
                            .giveWarningSound(giveWarningSound)
                            .runGrinder(runGrinder)
                            .build();
                    socketService.sendToAllClients(flagResponse);
                }
            }

        });
    }
}
