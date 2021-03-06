package com.shopme.admin.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.commom.entity.Role;
import com.shopme.commom.entity.User;

@Service
@Transactional
public class UserService {
	
	public static final int USER_PER_PAGE = 3;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public List<User> listAll() { 
		return (List<User>) userRepository.findAll();
	}
	
	public Page<User> listByPage(int pageNum, String keyword) { 
		Pageable pageable = PageRequest.of(pageNum - 1, USER_PER_PAGE);
		
		if (keyword != null) {
			return userRepository.findAll(keyword, pageable);
		}
		
		return userRepository.findAll(pageable);
	}
	
	public List<Role> listRoles() {
		return (List<Role>) roleRepository.findAll();
	}
	
	public User save(User user) {
		boolean isUpdate = (user.getId() != null);
		
		if(isUpdate) {
			User existingUser = userRepository.findById(user.getId()).get();
			
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			}else {
				encodePassword(user);
			}
		}else {
			encodePassword(user);
		}
			
		return userRepository.save(user);
	}
	
	private void encodePassword(User user) {
		String encode = passwordEncoder.encode(user.getPassword());
		user.setPassword(encode);
	}
	
	public boolean isEmailUnique(Integer id, String email) {
		User user = userRepository.getUserByEmail(email);
		
		if (user == null) return true;
		
		boolean isNew = (id == null);
		
		if(isNew) {
			if(user != null) return false;
		}else {
			if(user.getId() != id) {
				return false;
			}
		}
		
		return true;
	}

	public User get(int id) {
		try {
			return userRepository.findById(id).get();
		} catch (Exception e) {
			throw new UsernameNotFoundException("Could not find any user");
		}
		
	}
	
	public void delete(int id) {
		userRepository.deleteById(id);
	}
	
	public void updateEnable(Integer id, boolean enable) {
		userRepository.enableUser(id, enable);
	}
	
	public User getByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}
	
	public User updateAccount(User userInForm) {
		 User userInBD = userRepository.findById(userInForm.getId()).get();
		 
		 if (!userInForm.getPassword().isEmpty()) {
			userInBD.setPassword(userInForm.getPassword());
			encodePassword(userInBD);
		}
		
		 if (userInForm.getPhotos() != null) {
			userInBD.setPhotos(userInForm.getPhotos());
		}
		 
		userInBD.setFirstName(userInForm.getFirstName());
		userInBD.setLastName(userInForm.getLastName());
		
		return userRepository.save(userInBD);
	}
}
