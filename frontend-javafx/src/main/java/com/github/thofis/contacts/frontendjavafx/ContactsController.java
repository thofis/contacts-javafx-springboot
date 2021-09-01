package com.github.thofis.contacts.frontendjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import static javafx.collections.FXCollections.observableArrayList;

@Slf4j
public class ContactsController {

	private final ContactRestClient contactRestClient = new ContactRestClient();

	@FXML
	public ListView<Contact> contactListView;

	@FXML
	public Button exitButton;

	@FXML
	public Button editButton;

	@FXML
	public Button deleteButton;

	@FXML
	public Button newButton;


	@FXML
	public TextField lastName;

	@FXML
	public TextField firstName;

	@FXML
	public TextField email;

	@FXML
	public TextField phone;


	@FXML
	public void initialize() {
		log.debug("initialize");
		exitButton.setOnAction(event -> exit());
		deleteButton.setOnAction(event -> delete());
		newButton.setOnAction(event -> insert());
		editButton.setOnAction(event -> edit());
		loadContactList(contactListView);
		contactListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			log.debug("selectedContact: {}", newSelection);
			populateTextFields(newSelection);
		});
	}

	private void edit() {
		log.debug("todo");
	}

	private void insert() {
		log.debug("todo");
	}

	private void delete() {
		var selectedItem = contactListView.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			boolean success = contactRestClient.delete(selectedItem.getId());
			if (success) {
				contactListView.getItems().remove(selectedItem);
			}
		}
	}

	private void loadContactList(ListView<Contact> contactListView) {
		contactRestClient.getAllContacts()
				.thenAccept(contacts -> {
					var observableContacts = observableArrayList(contacts);
					contactListView.setItems(observableContacts);
					if (!observableContacts.isEmpty()) {
						populateTextFields(observableContacts.get(0));
						contactListView.getSelectionModel().selectFirst();
					}
				});
	}

	private void populateTextFields(Contact contact) {
		if (contact == null) {
			contact = Contact.builder().firstName("").lastName("").email("").phone("").build();
		}
		firstName.setText(contact.getFirstName());
		lastName.setText(contact.getLastName());
		email.setText(contact.getEmail());
		phone.setText(contact.getPhone());
	}

	public void exit() {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}

}
