package com.shopme.admin.product;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.user.UserCsvExporter;
import com.shopme.commom.entity.Product;
import com.shopme.commom.entity.User;

@Controller
public class ProductController {

	@Autowired
	ProductService service;
	
	@GetMapping("/products")
	public String listFirstPage(Model model) {
		return listByPage(1, model, null);
	}
	
	@GetMapping("/products/page/{pageNum}")
	public String listByPage(@PathVariable("pageNum")int pageNum, Model model,@Param("keyword") String keyword) {
		Page<Product> page = service.listByPage(pageNum, keyword );
		List<Product> listProducts = page.getContent();

		long totalItem = page.getTotalElements();
		int totalPage = page.getTotalPages();
		
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("totalItem", totalItem);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("keyword", keyword);
		
		return "products/products";
	}
	
	@GetMapping("/products/new")
	public String newProduct(Model model) {
		Product product = new Product();
		
		product.setEnable(true);
		product.setInStock(true);
		
		model.addAttribute("product", product);
		model.addAttribute("pageTitle", "Create New Product");
		
		return "products/product_form";
	}
	
	@PostMapping("/products/save")
	public String saveProduct(Product product,RedirectAttributes redirectAttributes, 
			@RequestParam("fileImage")MultipartFile multipartFile) throws IOException {
		
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			product.setImage(fileName);
			
			Product savedProduct = service.save(product);
			
			String uploadDir = "../product-images/" + savedProduct.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		} else {
			service.save(product);
		}
		
		redirectAttributes.addFlashAttribute("message", "Product saved");
		
		return "redirect:/products";
	}
	
	@GetMapping("/products/{id}/enable/{status}")
	public String updateEnable(@PathVariable("id")Integer id,@PathVariable("status") boolean enable,RedirectAttributes redirectAttributes) {
		
		service.updateEnable(id, enable);
		String msg = enable ? "Enabled" : "Disable";
		
		redirectAttributes.addFlashAttribute("message", msg);
		
		return "redirect:/products";
	}
	
	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, Model model) {

		try {
			service.delete(id);
			
			redirectAttributes.addFlashAttribute("message", "The product have been deleted");

		} catch (UsernameNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/products";
	}
	

	@GetMapping("/products/edit/{id}")
	public String editProduct(@PathVariable("id") int id, RedirectAttributes redirectAttributes, Model model) {
		Product product = service.get(id);
		
		model.addAttribute("product", product);
		model.addAttribute("pageTitle", "Edit Product");

		return "products/product_form";
	}
	
	@GetMapping("/products/export/csv")
	public void exportCSV(HttpServletResponse response) throws IOException {
		
		List<Product> listProducts = service.listAl();
		
		ProductCsvExporter  exporter = new ProductCsvExporter();
		
		exporter.export(listProducts, response);
	}
}
