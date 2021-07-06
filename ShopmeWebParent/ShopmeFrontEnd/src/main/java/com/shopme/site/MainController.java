package com.shopme.site;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopme.commom.entity.Product;
import com.shopme.site.product.ProductService;

@Controller
public class MainController {
	
	@Autowired
	ProductService service;
	
	@GetMapping("/")
	public String listFirstPage(Model model) {
		return listByPage(1, model,null);
	}
	
	@GetMapping("/page/{pageNum}")
	public String listByPage(@PathVariable("pageNum")int pageNum, Model model, @Param("keyword") String keyword) {
		Page<Product> page = service.listProductEnableByPage(pageNum,keyword);
		List<Product> listEnableProduct = page.getContent();

		long totalItem = page.getTotalElements();
		int totalPage = page.getTotalPages();
		
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("totalItem", totalItem);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("listEnableProduct", listEnableProduct);
		model.addAttribute("keyword", keyword);
		
		return "index";
	}
	
	@GetMapping("/login")
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		return "redirect:/";
	}
	
}
