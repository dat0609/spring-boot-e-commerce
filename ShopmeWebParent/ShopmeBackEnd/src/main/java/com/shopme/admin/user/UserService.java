package com.shopme.admin.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.commom.entity.Role;
import com.shopme.commom.entity.User;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public List<User> listAll() { 
		return (List<User>) userRepository.findAll();
	}
	
	public List<Role> listRoles() {
		return (List<Role>) roleRepository.findAll();
	}
	
	public void save(User user) {
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
			
		userRepository.save(user);
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
}
