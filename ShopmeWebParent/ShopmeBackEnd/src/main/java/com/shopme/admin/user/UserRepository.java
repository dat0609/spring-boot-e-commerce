package com.shopme.admin.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopme.commom.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

	@Query("Select u From User u Where u.email = :email")
	public User getUserByEmail(@Param("email")String email);
	
	@Query("Update User u Set u.enable = ?2 Where u.id = ?1")
	@Modifying
	public void enableUser(Integer id,boolean enable);
}
