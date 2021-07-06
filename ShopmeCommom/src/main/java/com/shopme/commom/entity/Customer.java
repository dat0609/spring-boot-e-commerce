package com.shopme.commom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class Customer {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 64,nullable = false, unique = true)
	private String email;
	
	@Column(length = 64)
	private String password;
	
	@Column(length = 64)
	private String name;
	
	@Column(name = "verification_code", length = 64)
	private String verificationCode;
	
	private boolean enable;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "authentication_type", length = 10)
	private AuthenticationType authenticationType;
	
	public Customer(int id) {
		this.id = id;
	}
	
	public Customer() {
	}

}
