package com.github.thofis.contacts.frontendjavafx;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {

	private Long id;

	private String firstName;

	private String lastName;

	private String email;

	private String phone;
}
