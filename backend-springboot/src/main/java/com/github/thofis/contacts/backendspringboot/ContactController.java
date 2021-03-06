package com.github.thofis.contacts.backendspringboot;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {
	private final ContactRepository contactRepository;

	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Contact>> getAll() {
		return ResponseEntity.ok(
				contactRepository.findAll()
		);
	}

	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Contact> get(@PathVariable("id") long id) {
		return ResponseEntity.ok(
				contactRepository
						.findById(id)
						.orElseThrow(() -> new NotFoundException("contact with id " + id + " not found"))
		);
	}

	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> post(@RequestBody Contact contact) {
		final var persistedContact = contactRepository.save(contact);
		return ResponseEntity.created(ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(persistedContact.getId())
						.toUri())
				.build();
	}

	@PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> put(@PathVariable("id") long id, @RequestBody Contact contact) {
		final var persistedContact = contactRepository.findById(id).orElseThrow(() -> new NotFoundException("contact with id " + id + " not found"));
		persistedContact.setFirstName(contact.getFirstName());
		persistedContact.setLastName(contact.getLastName());
		persistedContact.setEmail(contact.getEmail());
		persistedContact.setPhone(contact.getPhone());
		contactRepository.save(contact);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") long id) {
		contactRepository.findById(id).orElseThrow(() -> new NotFoundException("contact with id " + id + " not found"));
		contactRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
