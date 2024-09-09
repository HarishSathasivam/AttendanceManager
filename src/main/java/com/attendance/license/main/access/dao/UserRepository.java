package com.nusyn.license.main.access.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nusyn.license.main.access.pojo.User;





public interface UserRepository extends JpaRepository<User, String> {
	
	 List<User> findByUserId(String userId);
}
