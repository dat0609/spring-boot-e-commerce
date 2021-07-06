package com.shopme.site.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.commom.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

	@Query("Select p From Product p Where p.enable = true and p.name LIKE %?1%")
	public Page<Product> listAllEnable(Pageable pageable, String keyword);
	
	@Query("Select p From Product p Where p.enable = true ")
	public Page<Product> listAllPage(Pageable pageable);
	
}
