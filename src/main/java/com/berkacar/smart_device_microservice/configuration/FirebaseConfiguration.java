package com.berkacar.smart_device_microservice.configuration;

import com.berkacar.smart_device_microservice.utils.initializer.FirebaseInitializer;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@EnableConfigurationProperties(FirebaseProperties.class)
@Getter
@Setter
@AllArgsConstructor
public class FirebaseConfiguration {

    private FirebaseProperties firebaseProperties;

    @Bean("googleCredentialsFirebase")
    public GoogleCredentials googleCredentials() throws IOException {
        try (final InputStream inputStream = firebaseProperties.getResource().getInputStream()) {
            return GoogleCredentials.fromStream(inputStream);
        }
    }
    @Bean
    public Firestore firestore(@Qualifier("googleCredentialsFirebase") GoogleCredentials googleCredentials) {
        FirebaseInitializer.initialize(googleCredentials);
        return FirestoreClient.getFirestore();
    }
}
