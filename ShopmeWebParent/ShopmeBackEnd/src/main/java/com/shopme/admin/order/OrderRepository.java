package com.shopme.admin.order;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.commom.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, Integer>{

}
