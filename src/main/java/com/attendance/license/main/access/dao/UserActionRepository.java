package com.nusyn.license.main.access.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nusyn.license.main.access.pojo.Role;
import com.nusyn.license.main.access.pojo.User;
import com.nusyn.license.main.access.pojo.UserAction;





@Repository
public interface UserActionRepository extends JpaRepository<UserAction, Long> {

	UserAction save(UserAction userAction);
}