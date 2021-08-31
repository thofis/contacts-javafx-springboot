package com.github.thofis.contacts.frontendjavafx;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContactsController {

	static {
		Unirest.config()
				.setObjectMapper(new JacksonObjectMapper())
				.defaultBaseUrl("http://localhost:8080");
	}

	@FXML
	public ListView<Contact> contactListView;

	@FXML
	public Button exitButton;


	@FXML
	public void initialize() {
		log.debug("initialize");
		exitButton.setOnAction(event -> exit());
		loadContactList(contactListView);

	}

	private void loadContactList(ListView<Contact> contactListView) {
		Unirest.get("/api/contacts")
				.asObjectAsync(new GenericType<List<Contact>>() {})
				.thenAccept(response -> {
					var contactList = response.getBody();
					var observableContacts = FXCollections.observableArrayList(contactList);
					observableContacts.addListener((ListChangeListener<Contact>) c -> log.debug(c.toString()));
					observableContacts.add(
							Contact.builder()
									.firstName("bla")
									.build()
					);
					contactListView.setItems(observableContacts);
				});
	}

	public void exit() {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}
}
