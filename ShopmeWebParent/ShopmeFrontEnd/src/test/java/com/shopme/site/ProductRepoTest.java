package com.shopme.site;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.shopme.commom.entity.Product;
import com.shopme.site.product.ProductRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepoTest {

	@Autowired
	ProductRepository repository;
	

	
	@Test
	public void testGetProductById() {
		int id = 20;
		
		Optional<Product> product = repository.findById(id);
		System.out.println(product);
		assertThat(product).isNotNull();
	}
}
