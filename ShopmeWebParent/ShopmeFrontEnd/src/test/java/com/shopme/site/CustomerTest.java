package com.shopme.site;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.shopme.commom.entity.AuthenticationType;
import com.shopme.commom.entity.Customer;
import com.shopme.site.customer.CustomerRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerTest {

	@Autowired
	CustomerRepository repository;
	
	@Test
	public void testCustomer() {
		String email = "datlaq";
		
		Customer customer = repository.findByEmail(email);
		
		assertThat(customer).isNotNull();
	}
	
	@Test
	public void testUpdateAuthentication() {
		int id = 15;
		
		repository.updateAuthenticationType(id, AuthenticationType.FACEBOOK);
	}
	
	@Test
	public void testPassword() {
		String password = "123";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String encoded = encoder.encode(password);
		System.out.println(encoded);
		assertThat(encoded.equalsIgnoreCase(password));
	}
}
