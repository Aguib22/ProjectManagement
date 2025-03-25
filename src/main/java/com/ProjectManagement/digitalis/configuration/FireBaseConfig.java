package com.ProjectManagement.digitalis.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Aguibou sow
 * @date 2025-03-19 15:59
 * @package com.ProjectManagement.digitalis.configuration
 * @project digitalis
 */
@Configuration
public class FireBaseConfig {
    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/projectmanagement-7660a-firebase-adminsdk-fbsvc-2020cf2dc3.json"); // Remplace par ton fichier

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
