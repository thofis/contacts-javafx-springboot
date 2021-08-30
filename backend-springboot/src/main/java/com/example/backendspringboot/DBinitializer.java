package com.example.backendspringboot;

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
			contactRepository.save(
					Contact
							.builder()
							.firstName("John")
							.lastName("Miller")
							.email("john@miller.com")
							.phone("123")
							.build()
			);
			contactRepository.save(
					Contact
							.builder()
							.firstName("Jane")
							.lastName("Smith")
							.email("jane.smith@mail.com")
							.phone("234")
							.build()
			);
			contactRepository.flush();
		}
	}
}
