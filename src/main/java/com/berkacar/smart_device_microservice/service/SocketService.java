package com.berkacar.smart_device_microservice.service;

import com.berkacar.smart_device_microservice.request.FlagRequest;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SocketService {
    private final SocketIOServer socketIOServer;

    public SocketService(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
        socketIOServer.addConnectListener(socketIOClient -> log.info("ConnectedId: {}", String.valueOf(socketIOClient.getSessionId())));
        socketIOServer.addDisconnectListener(socketIOClient -> log.info("Dıs ConnectedId: {}", String.valueOf(socketIOClient.getSessionId())));
        socketIOServer.addEventListener("send_flag_request", FlagRequest.class,
                (socketIOClient, message, ackRequest) -> {
                    log.info("sender{} Sending message: {}", socketIOClient.getSessionId(), message);
                    socketIOClient.getNamespace().getAllClients().forEach(client -> {
                        if (!client.getSessionId().equals(socketIOClient.getSessionId())) {
                            client.sendEvent("get_flag_request", message);

                        }
                    });
                });
    }

    public void sendToAllClients(Object data) {
        socketIOServer.getAllClients().forEach(client -> {
            try {
                client.sendEvent("get_flag_request", data);
                log.info("Sent updated data to client: {}", client.getSessionId());
            } catch (Exception e) {
                log.error("Error sending data to client: {}", client.getSessionId(), e);
            }
        });
    }

}
