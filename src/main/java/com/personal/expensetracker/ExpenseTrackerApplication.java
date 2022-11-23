package com.personal.expensetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
public class ExpenseTrackerApplication {

	public static void main(String[] args) throws IOException {

		ClassLoader classLoader = ExpenseTrackerApplication.class.getClassLoader();

		File file = new File(Objects.requireNonNull(classLoader.getResource("fireBaseServiceAccountKey.json"))
				.getFile());

		FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://expensetracker-348008-default-rtdb.firebaseio.com")
				.build();

		FirebaseApp.initializeApp(options);

		SpringApplication.run(ExpenseTrackerApplication.class, args);
	}

}
