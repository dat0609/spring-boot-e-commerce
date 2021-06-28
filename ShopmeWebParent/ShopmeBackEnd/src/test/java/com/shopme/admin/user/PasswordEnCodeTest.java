package com.shopme.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEnCodeTest {

	@Test
	public void testPasswordEncode() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String rawPassString = "123";
		
		String encode = passwordEncoder.encode(rawPassString);
		System.out.println(encode);
	}
}
