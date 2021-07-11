package com.shopme.site.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopme.commom.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	
}
