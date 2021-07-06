package com.shopme.site.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopme.commom.entity.Customer;
import com.shopme.site.customer.CustomerRepository;

public class CustomerUserDetailService implements UserDetailsService{

	@Autowired
	CustomerRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = repo.findByEmail(email);
		
		if (customer == null) {
			throw new UsernameNotFoundException("Not found" + email);
		}
		return new CustomerUserDetail(customer);
	}

}
