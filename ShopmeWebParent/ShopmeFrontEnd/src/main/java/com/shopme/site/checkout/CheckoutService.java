package com.shopme.site.checkout;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shopme.commom.entity.CartItem;

@Service
public class CheckoutService {

	public CheckoutInfo prepareCheckout(List<CartItem> cartItems) {
		CheckoutInfo checkoutInfo = new CheckoutInfo();
		
		float productCost = calculateProductCost(cartItems);
		float productTotal = calculateProductTotal(cartItems);
		float paymentTotal = calculatePaymentTotal(cartItems);
		
		checkoutInfo.setProductCost(productCost);
		checkoutInfo.setProductTotal(productTotal);
		checkoutInfo.setDeliverDate(new Date());
		checkoutInfo.setPaymentTotal(paymentTotal);
		
		return checkoutInfo;
	}

	private float calculateProductTotal(List<CartItem> cartItems) {
		float total = 0;
		
		for(CartItem cartItem : cartItems) {
			total += cartItem.getQuantity() * cartItem.getProduct().getPrice();
			
		}
		return total;
	}
	

	private float calculateProductCost(List<CartItem> cartItems) {
		float cost = 0;
		
		for(CartItem cartItem : cartItems) {
			cost += cartItem.getQuantity() * cartItem.getProduct().getCost();
			
		}
		return cost;
	}
	
	private float calculatePaymentTotal(List<CartItem> cartItems) {
		float total = 0;
		
		for(CartItem cartItem : cartItems) {
			total += cartItem.getQuantity() * cartItem.getProduct().getPrice();
			
		}
		return total;
	}
}
