package com.shopme.site.customer;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.commom.entity.Customer;
import com.shopme.site.Utility;

@Controller
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		
		model.addAttribute("customer", new Customer());
		
		return "register/register_form";
	}
	
	@PostMapping("/create_customer")
	public String createCustomer(Customer customer,HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		customerService.registerCustomer(customer);
		String siteURL = Utility.getSite(request);
		
		customerService.sendVerificationEmail(customer, siteURL);
		
		return "register/register_success";
	}

	@GetMapping("/verify")
	public String verifyAccount(@Param("code")String code, Model model) {
		boolean verify = customerService.verify(code);
		
		return "register/" + (verify ? "verify_success" : "verify_fail");
	}
	
	@GetMapping("/account_detail")
	public String viewDetail(Model model, HttpServletRequest request) {
		String email = customerService.getEmailOfAuthenticatedCustomer(request);
		
		Customer customer = customerService.findByEmail(email);
		
		model.addAttribute("customer", customer);
		
		return"customer/account_form";
	}
	
	@PostMapping("/update_account_detail")
	public String updateAccountDetail(Model model, Customer customer, RedirectAttributes redirectAttributes) {
		customerService.updateAccount(customer);
		redirectAttributes.addFlashAttribute("message", "Account have been updated");
		
		return "redirect:/account_detail";
	}
	
}
