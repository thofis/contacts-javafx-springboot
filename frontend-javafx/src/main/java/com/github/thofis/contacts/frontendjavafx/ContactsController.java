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
	private ListView<Contact> contactListView;

	@FXML
	private Button exitButton;

	@FXML
	private Button editButton;

	@FXML
	private Button deleteButton;

	@FXML
	private Button addButton;


	@FXML
	private TextField lastName;

	@FXML
	private TextField firstName;

	@FXML
	private TextField email;

	@FXML
	private TextField phone;


	public void initialize() {
		log.debug("initialize");
		exitButton.setOnAction(event -> exit());
		deleteButton.setOnAction(event -> delete());
		addButton.setOnAction(event -> insert());
		editButton.setOnAction(event -> edit());
		loadContactList(contactListView);
		contactListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			log.debug("selectedContact: {}", newSelection);
			populateTextFields(newSelection);
		});
		editTextFields(false);
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

	private void editTextFields(boolean enabled) {
		firstName.setEditable(enabled);
		lastName.setEditable(enabled);
		email.setEditable(enabled);
		phone.setEditable(enabled);
	}

	public void exit() {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}

}
