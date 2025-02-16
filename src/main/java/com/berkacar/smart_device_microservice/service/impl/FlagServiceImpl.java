package com.berkacar.smart_device_microservice.service.impl;

import com.berkacar.smart_device_microservice.repository.FlagDataRepository;
import com.berkacar.smart_device_microservice.repository.entity.SmartDeviceFlag;
import com.berkacar.smart_device_microservice.response.FlagResponse;
import com.berkacar.smart_device_microservice.service.FlagService;
import com.berkacar.smart_device_microservice.service.SocketService;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.FirestoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.annotation.Nullable;
import java.time.Duration;

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
                    SmartDeviceFlag smartDeviceFlag = SmartDeviceFlag.builder()
                            .deviceId(deviceId)
                            .giveWarningSound(flagResponse.isGiveWarningSound())
                            .runGrinder(flagResponse.isRunGrinder())
                            .build();
                    if (runGrinder) {
                        smartDeviceFlag.setRunGrinder(!smartDeviceFlag.isRunGrinder());
                        runGrinder(smartDeviceFlag);
                    }
                    if (giveWarningSound) {
                        smartDeviceFlag.setGiveWarningSound(!smartDeviceFlag.isGiveWarningSound());
                        giveWarningSound(smartDeviceFlag);
                    }
                }
            }
        });
    }

    private void runGrinder(SmartDeviceFlag smartDeviceFlag) {
        try {
            WebClient webClient = WebClient.builder()
                    .baseUrl("http://192.168.1.200")
                    .clientConnector(new ReactorClientHttpConnector(HttpClient.create().responseTimeout(Duration.ofSeconds(10))))
                    .build();
            String response = webClient.get()
                    .uri("/process/run_grinder")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            flagDataRepository.updateFlag(smartDeviceFlag);
        } catch (Exception e) {
            flagDataRepository.updateFlag(smartDeviceFlag);
            System.err.println(e.getMessage());
        }
    }
    private void giveWarningSound(SmartDeviceFlag smartDeviceFlag) {
        try {
            WebClient webClient = WebClient.builder()
                    .baseUrl("http://192.168.1.200")
                    .clientConnector(new ReactorClientHttpConnector(HttpClient.create().responseTimeout(Duration.ofSeconds(10))))
                    .build();
            String response = webClient.get()
                    .uri("/process/give_warning_sound")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            flagDataRepository.updateFlag(smartDeviceFlag);

        } catch (Exception e) {
            flagDataRepository.updateFlag(smartDeviceFlag);
            System.err.println(e.getMessage());
        }

    }
}
