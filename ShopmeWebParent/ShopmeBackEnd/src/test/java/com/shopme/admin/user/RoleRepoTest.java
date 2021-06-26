package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.commom.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepoTest {

	@Autowired
	RoleRepository repository;
	
	
	@Test
	public void testRoleRepo() {
		Role roleAdmin = new Role("Admin","admin ne"); 
		Role saved = repository.save(roleAdmin);
		
		assertThat(saved.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testRole() {
		Role roleSale = new Role("Saleperson","sale person"); 

		Role roleEditor = new Role("Editor","editor"); 
		
		Role roleAssistant = new Role("Assistant","Assistant"); 

		repository.saveAll(List.of(roleAssistant,roleEditor,roleSale));

	}
}
