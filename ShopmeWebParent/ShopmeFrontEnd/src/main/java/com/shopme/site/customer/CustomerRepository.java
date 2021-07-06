package com.shopme.site.customer;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.commom.entity.AuthenticationType;
import com.shopme.commom.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	@Query("Select c From Customer c Where c.email = :email")
	public Customer findByEmail(@Param("email") String email);
	
	@Query("Select c From Customer c Where c.verificationCode = ?1")
	public Customer findByVerificationCode(String code);
	
	@Query("Update Customer c Set c.enable = true , c.verificationCode = null Where c.id = ?1")
	@Modifying
	public void enable(Integer id);
	
	@Query("Update Customer c Set c.authenticationType = ?2 where c.id = ?1")
	@Modifying
	public void updateAuthenticationType(Integer customerId, AuthenticationType type);
}
