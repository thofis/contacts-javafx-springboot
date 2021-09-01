package com.github.thofis.contacts.backendspringboot;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DBinitializer implements ApplicationRunner {
	private final ContactRepository contactRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		final var count = contactRepository.count();
		if (count == 0) {
			persistContact("John", "Miller", "john@miller.com", "123");
			persistContact("Jane", "Smith", "jane.smith@mail.com", "234");
			persistContact("Steven", "Brown", "steven.brown@mail.com", "345");
			persistContact("Liza", "Jones", "liza.jones@mail.com", "456");
			persistContact("Alexander", "Garcia", "alex.garcia@mail.com", "567");
			persistContact("Katie", "Davis", "katie.davis@mail.com", "678");
			contactRepository.flush();
		}
	}

	private void persistContact(String firstName, String lastName, String email, String phone) {
		contactRepository.save(
				Contact
						.builder()
						.firstName(firstName)
						.lastName(lastName)
						.email(email)
						.phone(phone)
						.build()
		);
	}
}
