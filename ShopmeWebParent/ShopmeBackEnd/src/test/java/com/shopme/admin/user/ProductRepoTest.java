package com.shopme.admin.user;
 	
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.product.ProductRepository;
import com.shopme.commom.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepoTest {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test 
	public void createProduct() {
		Product product = new Product();
		
		product.setName("Iphone A123");
		product.setShortDescription("Short Good");
		product.setFullDescription("Full Very good");
		product.setPrice(3000);
//		product.setEnable(true);
//		product.setInStock(true);
		product.setAlias("aaA");
		product.setCreateTime(new Date());
		product.setUpdateTime(new Date());
		
		Product savedProduct = productRepository.save(product);
		
		assertThat(savedProduct.getId()).isGreaterThan(0);
	}
	
	@Test
	public void listProduct() {
		List<Product> list = (List<Product>) productRepository.findAll();
		
		assertThat(list.size()).isGreaterThan(2);
	}
	
	@Test
	public void testSaveImage() {
		int id = 1;
		Product product = productRepository.findById(id).get();
		
		product.setImage("haha.jpg");
		
		Product savedProduct  = productRepository.save(product);
		
		assertThat(savedProduct.getImage().length()).isEqualByComparingTo(1);
	}
}
