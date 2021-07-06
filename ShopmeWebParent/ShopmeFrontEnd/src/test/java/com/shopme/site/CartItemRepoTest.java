package com.shopme.site;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.commom.entity.CartItem;
import com.shopme.commom.entity.Customer;
import com.shopme.commom.entity.Product;
import com.shopme.site.shoppingcart.CartItemRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CartItemRepoTest {
	
	@Autowired
	CartItemRepository repository;
	@Autowired
	TestEntityManager manager;
	
	@Test
	public void testCartItem() {
		Integer id = 1;
		int productId = 15;
		
		Customer customer = manager.find(Customer.class, id);
		Product product = manager.find(Product.class, productId);
		
		CartItem cartItem = new CartItem();
		cartItem.setCustomer(customer);
		cartItem.setProduct(product);
		cartItem.setQuantity(2);
		
		CartItem savedCartItem = repository.save(cartItem);
		
		assertThat(savedCartItem.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testFindByCustomer() {
		int id = 1;
		List<CartItem> list = repository.findByCustomer(new Customer(id));
		System.out.println(list);
		
		assertThat(list.size()).isEqualTo(1);
	}
	
	@Test
	public void testUpdateQuantity() {
		Integer id = 1;
		int productId = 15;
		int quantity = 3;
		
		repository.updateQuantity(quantity, id, productId);
	}
	
	@Test
	public void testDelete() {
		int customerId = 1;
		int productId = 15;
		
		repository.deleteByCustomerAndProduct(customerId, productId);
	}
}
