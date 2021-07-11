package com.shopme.site.shoppingcart;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shopme.commom.entity.CartItem;
import com.shopme.commom.entity.Customer;
import com.shopme.commom.entity.Product;

public interface CartItemRepository extends CrudRepository<CartItem, Integer>{
	
	List<CartItem> findByCustomer(Customer customer);
	
	CartItem findByCustomerAndProduct(Customer customer, Product product);
	
	@Query("Update CartItem c Set c.quantity = ?1 Where c.customer.id = ?2 and c.product.id = ?3")
	@Modifying
	void updateQuantity(int quantity, int customerId, int productId);
	
	@Query("Delete From CartItem c Where c.customer.id = ?1 and c.product.id = ?2")
	@Modifying
	void deleteByCustomerAndProduct(int customerId, int productId);
	
	@Query("Delete CartItem c Where c.customer.id = ?1")
	@Modifying
	void deleteByCustomer(Integer custometId);
}
