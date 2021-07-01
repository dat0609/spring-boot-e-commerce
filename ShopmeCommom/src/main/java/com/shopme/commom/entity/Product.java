package com.shopme.commom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, length = 256, nullable = false)
	private String name;
	
	@Column(unique = true, length = 256, nullable = false)
	private String alias;
	
	@Column(length = 256, nullable = false, name = "short_description")
	private String shortDescription;
	
	@Column(length = 2560, nullable = false,name = "full_description")
	private String fullDescription;
	
	
	@Column(name = "created_time")
	private Date createTime;
	
	@Column(name = "updated_time")
	private Date updateTime;
	
	private boolean enable;
	
	@Column(name = "in_stock")
	private boolean inStock;
	
	private float cost;
	private float price;
	
	@Column(name = "discount_percent")
	private float discountPercent;
	
	private float length;
	private float width;
	private float height;
	private float weight;
	
	private String image;
	
	@Transient
	public String getImg() {
		if (id == null || image == null) {
			return "/images/ShopmeAdminSmall.png";
		}
		return "/product-images/" + this.id + "/" + this.image;
	}
}
