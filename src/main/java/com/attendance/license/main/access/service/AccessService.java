package com.nusyn.license.main.access.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.nusyn.license.exception.PlatformException;

import com.nusyn.license.main.access.model.RoleTO;
import com.nusyn.license.main.access.model.UserActionTO;
import com.nusyn.license.main.access.model.UserTO;
import com.nusyn.license.main.access.pojo.User;
import com.nusyn.license.main.access.pojo.UserAction;





@Repository
public interface AccessService {
	
	public UserTO getUserByUserId(String userId,boolean loadDetails) throws PlatformException;
	public UserTO saveUser(UserTO userTO,boolean createNewUser,UserActionTO userActionTO) throws PlatformException;
	public UserTO saveLoggedOurUser(UserTO userTO) throws PlatformException;
	public UserTO deleteUser(String userId) throws PlatformException;

	

	public List<UserTO> getUsers() throws PlatformException;
	
	//Role
	public RoleTO saveRole(RoleTO roleTO) throws PlatformException;
	public List<RoleTO> getRolesByName(String roleName, boolean loadDetails) throws PlatformException;
	
	
}
