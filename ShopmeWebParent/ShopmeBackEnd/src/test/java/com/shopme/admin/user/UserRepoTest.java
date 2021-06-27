package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.commom.entity.Role;
import com.shopme.commom.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepoTest {

	@Autowired
	UserRepository repository;

	@Autowired
	TestEntityManager entity;

	@Test
	public void testCreateUser() {
		Role roleAdminRole = entity.find(Role.class, 2);

		User user = new User("dat", "123", "Dat", "Le");
		user.addRole(roleAdminRole);

		User savedUser = repository.save(user);

		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateWith2Role() {
		User user = new User("tuan@gmail.com", "123", "tuan", "le");

		Role editoRole = new Role(4);
		Role saleRole = new Role(5);

		user.addRole(saleRole);
		user.addRole(editoRole);

		User savedUser = repository.save(user);

		assertThat(savedUser.getId()).isGreaterThan(0);

	}

	@Test
	public void testListUser() {
		Iterable<User> listIterable = repository.findAll();

		assertThat(listIterable).isNotEmpty();
	}

	@Test
	public void getUserById() {
		User user = repository.findById(4).get();

		assertThat(user).isNotNull();
	}

	@Test
	public void updateUser() {
		User user = repository.findById(4).get();
		user.setEnable(true);
		
		User savedUser = repository.save(user);
		
		System.out.println(savedUser.isEnable());
		assertThat(savedUser).isNotNull();
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "dat";
		
		User user =  repository.getUserByEmail(email);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testEnable() {
		Integer id = 5;
		repository.enableUser(id, true);
	}
	
	@Test
	public void testListFirstPage() {
		int pageNum = 0;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		
		Page<User> page = repository.findAll(pageable);
		
		List<User> list = page.getContent();
		
		list.forEach(user -> System.out.println(user));
		
		assertThat(list.size()).isEqualTo(pageSize);
	}
	
	@Test
	public void testSearch() {
		String keyword = "t";
		int pageNum = 0;
		int pageSize = 3;
		
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		
		Page<User> page = repository.findAll(keyword, pageable);
		
		List<User> list = page.getContent();
		
		list.forEach(user -> System.out.println(user));
		
		assertThat(list.size()).isGreaterThan(0);
	}
}
