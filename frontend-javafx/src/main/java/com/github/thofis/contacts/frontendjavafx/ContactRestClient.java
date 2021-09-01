package com.github.thofis.contacts.frontendjavafx;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContactRestClient {

	static {
		Unirest.config()
				.setObjectMapper(new JacksonObjectMapper())
				.defaultBaseUrl("http://localhost:8080");
	}

	public CompletableFuture<List<Contact>> getAllContacts() {
		return Unirest.get("/api/contacts")
				.asObjectAsync(new GenericType<List<Contact>>() {})
				.thenApply(HttpResponse::getBody);
	}

	public boolean delete(long id) {
		var response = Unirest.delete("/api/contacts/{id}").routeParam("id", "" + id).asEmpty();
		log.debug("Status: {} - {}", response.getStatus(), response.getStatusText());
		return response.getStatus() == 204;
	}

//	private void loadContactList(ListView<Contact> contactListView) {
//		Unirest.get("/api/contacts")
//				.asObjectAsync(new GenericType<List<Contact>>() {})
//				.thenAccept(response -> {
//					var contactList = response.getBody();
//					var observableContacts = FXCollections.observableArrayList(contactList);
//					observableContacts.addListener((ListChangeListener<Contact>) c -> log.debug(c.toString()));
//					observableContacts.add(
//							Contact.builder()
//									.firstName("bla")
//									.build()
//					);
//					contactListView.setItems(observableContacts);
//				});
//	}

}
