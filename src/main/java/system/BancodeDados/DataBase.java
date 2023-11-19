package system.BancodeDados;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.IOException;
import java.io.InputStream;

public class DataBase {
        private static boolean isInitialized = false;

        public static void initialize() throws IOException {
                if (!isInitialized) {

                        InputStream serviceAccount = DataBase.class.getClassLoader()
                                        .getResourceAsStream(
                                                        "sistema-loja-bf8c2-firebase-adminsdk-1suld-d84f0b66e7.json");

                        if (serviceAccount == null) {
                                throw new IOException(
                                                "Não foi possível encontrar o arquivo de credenciais do Firebase.");
                        }

                        FirebaseOptions options = FirebaseOptions.builder()
                                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                                        .setDatabaseUrl("https://sistema-loja-bf8c2-default-rtdb.firebaseio.com")
                                        .build();

                        FirebaseApp.initializeApp(options);
                        isInitialized = true;

                }
        }
}
