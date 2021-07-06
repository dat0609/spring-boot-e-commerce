package com.shopme.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEnCodeTest {

	@Test
	public void testPasswordEncode() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String rawPassString = "123";
		
		String encode = passwordEncoder.encode(rawPassString);
		
		
		if( passwordEncoder.matches(rawPassString, encode)) {
			System.out.println(encode);
		}

	}
}
