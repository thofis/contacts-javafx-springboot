package com.github.thofis.contacts.frontendjavafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ContactsApplication extends Application {
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(ContactsApplication.class.getResource("contacts-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("Contacts Application");
		stage.setScene(scene);
		stage.show();
	}
}
