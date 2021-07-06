package com.shopme.site.shoppingcart;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.commom.entity.Customer;
import com.shopme.site.customer.CustomerService;

@RestController
public class ShoppingCartRestController {

	@Autowired
	ShoppingCartService service;
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/cart/add/{productId}/{quantity}")
	public String addProductToCart(@PathVariable("productId")Integer productId,
			@PathVariable("quantity")Integer quantity, HttpServletRequest request) {
		
		try {
			Customer customer = getAuthenticatedCustomer(request);
			service.addProduct(productId, quantity, customer);
			
			return quantity + " item added to you cart";
		} catch (Exception e) {
			return "You must login";
		}
	}
	
	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
		String email = customerService.getEmailOfAuthenticatedCustomer(request);
		
		if (email == null) {
			throw new UsernameNotFoundException("Not found user");
		}
		
		return customerService.findByEmail(email);
		
	}
	
	@PostMapping("/cart/update/{productId}/{quantity}")
	public String updateQuantity(@PathVariable("productId")Integer productId,
			@PathVariable("quantity")Integer quantity, HttpServletRequest request) {

		try {
			Customer customer = getAuthenticatedCustomer(request);
			float subtotal = service.updateQuantity(productId, quantity, customer);
				
			return String.valueOf(subtotal);
		} catch (Exception e) {
			return "You must login";
		}
	}
	
	@DeleteMapping("/cart/remove/{productId}")
	public String removeProduct(@PathVariable("productId")Integer productId, HttpServletRequest request) {
		
		Customer customer = getAuthenticatedCustomer(request);
		
		service.removeProduct(productId, customer);
		
		return "The product has been remove";
	}
}
