package com.shopme.site.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shopme.commom.entity.Product;

@Service
public class ProductService {
	
	private static final int PRODUCT_PER_PAGE = 10;

	@Autowired
	private ProductRepository repository;
	
	public Page<Product> listProductEnableByPage(int pageNum, String keyword) { 
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCT_PER_PAGE);
		
		if (keyword != null) {
			return repository.listAllEnable(pageable, keyword);
		}
		
		return repository.listAllPage(pageable);
	}
	
	public Product getProductById(Integer id) {
		return repository.findById(id).get();
	}
	
//	public Page<Product> search(String keyword, Pageable pageable) {
//		
//	}
}
