package com.shopme.site.order;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.shopme.commom.entity.CartItem;
import com.shopme.commom.entity.Customer;
import com.shopme.commom.entity.Order;
import com.shopme.commom.entity.OrderDetail;
import com.shopme.commom.entity.OrderStatus;
import com.shopme.commom.entity.PaymentMethod;
import com.shopme.commom.entity.Product;
import com.shopme.site.checkout.CheckoutInfo;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	JavaMailSender mailSender;
	
	public Order createOrder(Customer customer, List<CartItem> cartItems, 
			PaymentMethod paymentMethod, CheckoutInfo checkoutInfo,HttpServletRequest request, String address,String name, String phone) {
		
		Order order = new Order();
		order.setOrderTime(new Date());
		order.setOrderStatus(OrderStatus.NEW);
		order.setCustomer(customer);
		order.setProductCost(checkoutInfo.getProductCost());
		order.setShippingCost(checkoutInfo.getShippingCost());
		order.setTotal(checkoutInfo.getPaymentTotal());
		order.setPaymentMethod(paymentMethod);
		order.setDeliverDate(checkoutInfo.getDeliverDate());
		order.setAddress(address);
		order.setName(name);
		order.setPhoneNumber(phone);
		
		Set<OrderDetail> orderDetails = order.getOrderDetails();
		
		for(CartItem item : cartItems) {
			Product product = item.getProduct();
			
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(order);
			orderDetail.setProduct(product);
			orderDetail.setQuantity(item.getQuantity());
			orderDetail.setProductCost(product.getCost() * item.getQuantity());
			orderDetails.add(orderDetail);
		}
		
		return orderRepository.save(order);
	}
	
	public void sendOrderConfirmationEmail(HttpServletRequest request, Order order,String siteURL) throws UnsupportedEncodingException, MessagingException {
		String sub = "Verify for your order";
		String senderName = "ShopMe";
		String content = "<p>Dear " + order.getCustomer().getName() + ",</p>";
		content += "<p> This email is to confirm that you have successfully placed an order through our website: </p>"
				+ "<p>OrderId: " + order.getId() + "<br>" +
					  "Order Time: " + order.getOrderTime() + "<br>" +
					  "Total: " +order.getTotal() + "<br>" +
					  "Payment: " +order.getPaymentMethod() + "<br>" +
					"</p>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("datisme731@gmail.com", senderName);
		helper.setTo(order.getCustomer().getEmail());
		helper.setSubject(sub);
		helper.setText(content, true);

		mailSender.send(message);
	}
}
