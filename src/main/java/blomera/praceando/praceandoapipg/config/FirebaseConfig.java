/*
 * Class: FirebaseConfig
 * Description: Configuration for Firebase in the application. This class sets up and initializes the Firebase client
 *              using credentials provided by an environment variable and defines the Firebase storage bucket.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 08/29/2024
 * Last Updated: 08/29/2024
 */
package blomera.praceando.praceandoapipg.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        String firebaseConfig = System.getenv("FIREBASE_SERVICE_ACCOUNT_JSON");
        if (firebaseConfig == null || firebaseConfig.isEmpty()) {
            throw new IllegalStateException("A variável de ambiente FIREBASE_SERVICE_ACCOUNT_JSON não está configurada.");
        }
        InputStream serviceAccount = new ByteArrayInputStream(firebaseConfig.getBytes(StandardCharsets.UTF_8));

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("praceandoimages.appspot.com")
                .build();

        return FirebaseApp.initializeApp(options);
    }
}

