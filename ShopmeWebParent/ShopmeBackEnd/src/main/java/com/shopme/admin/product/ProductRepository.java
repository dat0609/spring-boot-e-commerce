package com.shopme.admin.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shopme.commom.entity.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>{

	@Query("Update Product p Set p.enable = ?2 Where p.id = ?1")
	@Modifying
	public void enableProduct(Integer id,boolean enable);
	
	@Query("Select p From Product p Where p.name Like %?1%")
	public Page<Product> findAll(String keyword, Pageable pageable);
}
