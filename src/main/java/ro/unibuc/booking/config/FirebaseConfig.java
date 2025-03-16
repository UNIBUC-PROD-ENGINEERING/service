package ro.unibuc.booking.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.context.annotation.Bean;


@Configuration
public class FirebaseConfig {

    @Value("${firebase.key.path:/secrets/firebase-key}")
    private String firebaseKeyPath;

    @Bean
    public Bucket initFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream(firebaseKeyPath);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("booking-vtm.firebasestorage.app")
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            System.out.println("✅ Firebase Admin SDK initialized successfully.");

            Bucket defaultBucket = StorageClient.getInstance(FirebaseApp.getInstance()).bucket("booking-vtm.firebasestorage.app");

            return defaultBucket;

        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to initialize Firebase Admin SDK", e);
        }
    }
}
