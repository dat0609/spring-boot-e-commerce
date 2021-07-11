package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.order.OrderRepository;
import com.shopme.commom.entity.Customer;
import com.shopme.commom.entity.Order;
import com.shopme.commom.entity.OrderDetail;
import com.shopme.commom.entity.OrderStatus;
import com.shopme.commom.entity.PaymentMethod;
import com.shopme.commom.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class OrderRepoTest {

	@Autowired
	OrderRepository repository;
	
	@Autowired
	TestEntityManager manager;
	
	@Test
	public void testAddOrderWith1Product() {
		Customer customer = manager.find(Customer.class, 2);
		Product product = manager.find(Product.class, 17);
		
		Order mainOrder = new Order();
		mainOrder.setOrderTime(new Date());
		mainOrder.setCustomer(customer);
		mainOrder.setName(customer.getName());
		mainOrder.setPhoneNumber("0111111");
		mainOrder.setAddress("HN");
		
		mainOrder.setShippingCost(10);
		mainOrder.setProductCost(product.getCost());
		mainOrder.setTotal(product.getPrice() + 10);
		
		mainOrder.setPaymentMethod(PaymentMethod.COD);
		mainOrder.setOrderStatus(OrderStatus.NEW);
		mainOrder.setDeliverDate(new Date());
		
		OrderDetail orderDetail = new OrderDetail();
		
		orderDetail.setProduct(product);
		orderDetail.setOrder(mainOrder);
		orderDetail.setProductCost(product.getCost());
		orderDetail.setShippingCost(10);
		orderDetail.setQuantity(1);
		
		mainOrder.getOrderDetails().add(orderDetail);
		
		Order savedOrder = repository.save(mainOrder);
		
		assertThat(savedOrder.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testAddNewOrderWithMulProduct() {
		Customer customer = manager.find(Customer.class, 1);
		Product product1 = manager.find(Product.class, 18);
		Product product2 = manager.find(Product.class, 19);
		
		Order mainOrder = new Order();
		


		OrderDetail orderDetail1 = new OrderDetail();
		
		orderDetail1.setProduct(product1);
		orderDetail1.setOrder(mainOrder);
		orderDetail1.setProductCost(product1.getCost());
		orderDetail1.setShippingCost(10);
		orderDetail1.setQuantity(1);
		
		mainOrder.getOrderDetails().add(orderDetail1);
		
		
		
		OrderDetail orderDetail2 = new OrderDetail();
		
		orderDetail2.setProduct(product2);
		orderDetail2.setOrder(mainOrder);
		orderDetail2.setProductCost(product2.getCost());
		orderDetail2.setShippingCost(10);
		orderDetail2.setQuantity(2);
		
		mainOrder.getOrderDetails().add(orderDetail2);
		
		
		mainOrder.setOrderTime(new Date());
		mainOrder.setCustomer(customer);
		mainOrder.setName(customer.getName());
		mainOrder.setPhoneNumber("0111111");
		mainOrder.setAddress("HN");
		
		mainOrder.setProductCost(product1.getCost() + product2.getCost());
		mainOrder.setTotal(product1.getPrice() + product2.getPrice() + 10);
		
		mainOrder.setPaymentMethod(PaymentMethod.CREDIT_CARD);
		mainOrder.setOrderStatus(OrderStatus.PROCESSING);
		mainOrder.setDeliverDate(new Date());
		
		Order savedOrder = repository.save(mainOrder);
		assertThat(savedOrder.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListOrders() {
		Iterable<Order> listIterable = repository.findAll();
		
		assertThat(listIterable).hasSizeGreaterThan(0);
		
	}
	
	@Test
	public void testUpdateOrder() {
		int id = 2;
		Order order = repository.findById(id).get();
		
		order.setOrderStatus(OrderStatus.SHIPPING);
		order.setPaymentMethod(PaymentMethod.COD);
		
		Order savOrder = repository.save(order);
		
		assertThat(savOrder.getOrderStatus().equals(OrderStatus.SHIPPING));
	}
}
