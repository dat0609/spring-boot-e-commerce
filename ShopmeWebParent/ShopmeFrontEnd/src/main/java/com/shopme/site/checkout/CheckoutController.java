package com.shopme.site.checkout;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopme.commom.entity.CartItem;
import com.shopme.commom.entity.Customer;
import com.shopme.commom.entity.Order;
import com.shopme.commom.entity.PaymentMethod;
import com.shopme.site.customer.CustomerService;
import com.shopme.site.order.OrderService;
import com.shopme.site.shoppingcart.ShoppingCartService;

@Controller
public class CheckoutController {

	@Autowired
	CheckoutService checkoutService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ShoppingCartService shoppingCartService;
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/checkout")
	public String showCheckoutPage(Model model, HttpServletRequest request) {
		Customer customer = getAuthenticatedCustomer(request);
		List<CartItem> list = shoppingCartService.listCartItems(customer);
		
		CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(list);
		
		model.addAttribute("customer", customer);
		model.addAttribute("checkoutInfo", checkoutInfo);
		model.addAttribute("list", list);	
		
		return "checkout/checkout";
	}
	
	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
		String email = customerService.getEmailOfAuthenticatedCustomer(request);
		
		return customerService.findByEmail(email);
		
	}
	
	@PostMapping("/place_order")
	public String placeOrder(HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		String paymentType = request.getParameter("COD");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentType);
		Customer customer = getAuthenticatedCustomer(request);
		
		List<CartItem> list = shoppingCartService.listCartItems(customer);
		
		CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(list);
		
		Order order = orderService.createOrder(customer, list, paymentMethod, checkoutInfo, request, address, name, phone);
		orderService.sendOrderConfirmationEmail(request, order, null);
		shoppingCartService.deleteByCustomer(customer);
		
		return "checkout/order_completed";
	}
}
