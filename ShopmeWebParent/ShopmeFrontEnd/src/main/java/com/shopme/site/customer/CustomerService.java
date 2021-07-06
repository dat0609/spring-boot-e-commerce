package com.shopme.site.customer;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.shopme.commom.entity.AuthenticationType;
import com.shopme.commom.entity.Customer;
import com.shopme.site.security.oauth.CustomerOauth2User;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private JavaMailSender mailSender;

	public boolean isEmailUnique(String email) {
		Customer customer = customerRepository.findByEmail(email);

		return customer == null;
	}

	public void registerCustomer(Customer customer) {

		String encode = passwordEncoder.encode(customer.getPassword());

		customer.setPassword(encode);
		customer.setEnable(false);
		customer.setCreateTime(new Date());
		customer.setAuthenticationType(AuthenticationType.DATABASE);

		String randomCode = RandomString.make(64);
		customer.setVerificationCode(randomCode);

		customerRepository.save(customer);

	}

	public void sendVerificationEmail(Customer customer, String siteURL)
			throws UnsupportedEncodingException, MessagingException {
		String sub = "Verify for your registration";
		String senderName = "ShopMe";
		String content = "<p>Dear " + customer.getName() + ",</p>";
		content += "<p> Click the link below to verify: </p>";

		String verifyURL = siteURL + "/verify?code=" + customer.getVerificationCode();
		content += "<h3><a href=\"" + verifyURL + "\">VERIFY</a></h3>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("datisme731@gmail.com", senderName);
		helper.setTo(customer.getEmail());
		helper.setSubject(sub);
		helper.setText(content, true);

		mailSender.send(message);
	}

	public boolean verify(String code) {
		Customer customer = customerRepository.findByVerificationCode(code);

		if (customer == null || customer.isEnable()) {
			return false;
		} else {
			customerRepository.enable(customer.getId());
			return true;
		}
	}

	public void updateAuthenticationType(Customer customer, AuthenticationType type) {
		if (!customer.getAuthenticationType().equals(type)) {
			customerRepository.updateAuthenticationType(customer.getId(), type);
		}
	}

	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	public void addCustomerUponOauth2Login(String name, String email, AuthenticationType authenticationType) {

		Customer customer = new Customer();
		customer.setEmail(email);
		customer.setName(name);
		customer.setEnable(true);
		customer.setCreateTime(new Date());
		customer.setPassword(null);
		customer.setAuthenticationType(authenticationType);

		customerRepository.save(customer);
	}

	public String getEmailOfAuthenticatedCustomer(HttpServletRequest request) {
		Object principal = request.getUserPrincipal();

		if (principal == null) return null;
		
		String email = null;

		if (principal instanceof UsernamePasswordAuthenticationToken
				|| principal instanceof RememberMeAuthenticationToken) {
			email = request.getUserPrincipal().getName();
		} else if (principal instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) principal;
			CustomerOauth2User oauth2User = (CustomerOauth2User) oAuth2AuthenticationToken.getPrincipal();
			email = oauth2User.getEmail();
		}

		return email;
	}

	public Customer updateAccount(Customer customerInForm) {
		Customer customerInBD = customerRepository.findById(customerInForm.getId()).get();

		if (customerInBD.getAuthenticationType().equals(AuthenticationType.DATABASE)) {
			if (!customerInForm.getPassword().isEmpty()) {
				customerInBD.setPassword(customerInForm.getPassword());

				String encode = passwordEncoder.encode(customerInForm.getPassword());

				customerInForm.setPassword(encode);
			} else {
				customerInForm.setPassword(customerInBD.getPassword());
			}
		}

		customerInForm.setEnable(customerInBD.isEnable());
		customerInForm.setCreateTime(customerInBD.getCreateTime());
		customerInForm.setVerificationCode(customerInBD.getVerificationCode());
		customerInForm.setAuthenticationType(customerInBD.getAuthenticationType());

		return customerRepository.save(customerInForm);
	}
}
