package com.shopme.site.shoppingcart;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.commom.entity.CartItem;
import com.shopme.commom.entity.Customer;
import com.shopme.commom.entity.Product;
import com.shopme.site.product.ProductRepository;

@Service
@Transactional
public class ShoppingCartService {

	@Autowired
	CartItemRepository repository;
	@Autowired
	ProductRepository productRepository;
	
	public Integer addProduct(Integer productId, Integer quantity, Customer customer) {
		Integer updateQuantity = quantity;
		Product product = new Product(productId);
		
		CartItem cartItem = repository.findByCustomerAndProduct(customer, product);
		
		if (cartItem != null) {
			updateQuantity = cartItem.getQuantity() + quantity;
		}else {
			cartItem = new CartItem();
			cartItem.setCustomer(customer);
			cartItem.setProduct(product);
		}
		
		cartItem.setQuantity(updateQuantity);
		
		repository.save(cartItem);
		
		return updateQuantity;

	}
	
	public List<CartItem> listCartItems(Customer customer) {
		return repository.findByCustomer(customer);
	}
	
	public float updateQuantity(Integer productId, Integer quantity, Customer customer) {
		repository.updateQuantity(quantity, customer.getId(), productId);
		Product product = productRepository.findById(productId).get();
		
		float subTotal = product.getPrice() * quantity;

		return subTotal;
	}
	
	public void removeProduct(Integer productId, Customer customer) {
		repository.deleteByCustomerAndProduct(customer.getId(), productId);
	}
}
