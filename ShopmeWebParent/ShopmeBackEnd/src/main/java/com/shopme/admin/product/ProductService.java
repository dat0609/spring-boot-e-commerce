package com.shopme.admin.product;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shopme.commom.entity.Product;

@Service
@Transactional
public class ProductService {

	private static final int PRODUCT_PER_PAGE = 10;

	@Autowired
	ProductRepository productRepository;
	
	public List<Product> listAl(){
		return (List<Product>) productRepository.findAll();
	}
	
	public Page<Product> listByPage(int pageNum, String keyword) { 
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCT_PER_PAGE);
		
		if (keyword != null) {
			return productRepository.findAll(keyword, pageable);
		}
		
		return productRepository.findAll(pageable);
	}
	
	public Product save(Product product) {
		if (product.getId() == null) {
			product.setCreateTime(new Date());
		}
		if (product.getAlias() == null || product.getAlias().isEmpty()) {
			String defaultAlias = product.getName().replaceAll(" ", "-");
			product.setAlias(defaultAlias);
		}else {
			product.setAlias(product.getAlias().replaceAll(" ", "-"));
		}
		
		product.setUpdateTime(new Date());
		
		return productRepository.save(product);
	}
	
	public void updateEnable(Integer id, boolean enable) {
		productRepository.enableProduct(id, enable);
	}
	
	public void delete(int id) {
		productRepository.deleteById(id);
	}
	
	public Product get(Integer id) {
		return productRepository.findById(id).get();
	}
	
	public void updateStockProduct(Integer id, boolean inStock) {
		 productRepository.UpdateInstockProduct(id, inStock);
	}
}
