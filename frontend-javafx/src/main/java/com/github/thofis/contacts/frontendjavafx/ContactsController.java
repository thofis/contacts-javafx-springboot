package com.github.thofis.contacts.frontendjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContactsController {

	@FXML
	public ListView<Contact> contactList;

	@FXML
	public Button exitButton;

	@FXML
	public void initialize() {
		log.debug("initialize");
		exitButton.setOnAction(event -> exit());
	}

	public void exit() {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}
}
