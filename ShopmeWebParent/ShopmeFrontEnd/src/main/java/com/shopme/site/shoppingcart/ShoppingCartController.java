package com.shopme.site.shoppingcart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.commom.entity.CartItem;
import com.shopme.commom.entity.Customer;
import com.shopme.site.customer.CustomerService;

@Controller
public class ShoppingCartController {

	@Autowired
	ShoppingCartService service;
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/cart")
	public String viewCart(Model model, HttpServletRequest request) {
		Customer customer = getAuthenticatedCustomer(request);
		List<CartItem> list = service.listCartItems(customer);
		
		float total = 0;
		
		for(CartItem cartItem : list) {
			total += cartItem.getQuantity() * cartItem.getProduct().getPrice();
		}
		
		model.addAttribute("total", total);
		model.addAttribute("list", list);
		
		return "cart/shopping_cart";
	}
	
	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
		String email = customerService.getEmailOfAuthenticatedCustomer(request);
		
		return customerService.findByEmail(email);
		
	}
}
