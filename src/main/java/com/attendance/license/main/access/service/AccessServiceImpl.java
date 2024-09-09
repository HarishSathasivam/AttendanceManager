package com.nusyn.license.main.access.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nusyn.license.exception.PlatformException;

import com.nusyn.license.main.access.dao.RoleRepository;
import com.nusyn.license.main.access.dao.UserActionRepository;
import com.nusyn.license.main.access.dao.UserRepository;

import com.nusyn.license.main.access.model.RoleTO;
import com.nusyn.license.main.access.model.UserActionTO;
import com.nusyn.license.main.access.model.UserTO;

import com.nusyn.license.main.access.pojo.Role;
import com.nusyn.license.main.access.pojo.User;
import com.nusyn.license.main.access.pojo.UserAction;






@Service
public class AccessServiceImpl 	implements AccessService {

	
	 
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserActionRepository userActionRepository;
	
	@Override
	public UserTO getUserByUserId(String userId,boolean loadDetails) throws PlatformException 
	{
		UserTO userTO = null;
		
		try
		{	List<User> userPojos ;		
			if (userId != null && !userId.isEmpty()) {
				userPojos =  userRepository.findByUserId(userId);
            } else {
            	userPojos = userRepository.findAll();
            }			
			
			if(userPojos != null)
			{
				if(userPojos.size() == 0)
				{
					//No user found.
				}else if(userPojos.size() > 1)
				{
					//There are too many users with this name. Log it
					User userPojo = userPojos.get(0);

					userTO = AccessConverters.pOjoToTO(userPojo);

				}else
				{
					User userPojo = userPojos.get(0);

					userTO = AccessConverters.pOjoToTO(userPojo);
				}
				

			}
			
		}catch(Exception exp)
		{
			throw new PlatformException("Exception getting user by userId " + userId, exp);


		}
		
		return userTO;
	}
	
	//Role
		@Override
		public RoleTO saveRole(RoleTO roleTO) throws PlatformException	
		{
			try
			{
				//Check if role exists
				Role roleToSave = AccessConverters.TOToPojo(roleTO);
				if(roleToSave.getId() != null)
				{
					//Role exists. Update it
					roleRepository.save(roleToSave);
				}else
				{
					
					Role savedRole = roleRepository.save(roleToSave);
					Integer newRoleId = savedRole.getId();
					roleTO.setId(newRoleId);
					
				}
			}catch(Exception exp)
			{
				throw new PlatformException("Exception saving Role ", exp);
			}
			return roleTO;
		}
		
		
		@Override
		public List<RoleTO> getRolesByName(String roleName,boolean loadDetails) throws PlatformException
		{
			List<RoleTO> roleTOs = new ArrayList<RoleTO>();
			Role role =  new Role();
			
			List<Role> roleList = new ArrayList<Role>();

			try
			{
				
				List<Role> rolePojos =  roleRepository.findByName(roleName);

				if(rolePojos != null)
				{
					roleTOs = AccessConverters.rolePojoToTO(rolePojos,loadDetails);
				}
			}catch(Exception exp)
			{
				throw new PlatformException("Exception getting roles by name " + roleName, exp);
			}
			
			return roleTOs;
		}

		@Override
		public UserTO saveUser(UserTO userTO,boolean createNewUser , UserActionTO userActionTO) throws PlatformException	
		{
			try
			{

				if(createNewUser) {

					User userToSave = AccessConverters.TOToPojo(userTO);
					userRepository.save(userToSave);
				}else{
			    	
					if(userActionTO != null) {
					    UserAction userAction = AccessConverters.UserActionTOToPojo( userActionTO);
						User userToSave = AccessConverters.TOToPojo(userTO);
	
	
						userAction.setUser(userToSave);
						
						if (userToSave.getUserActions() == null) {
							userToSave.setUserActions(new HashSet<>());
				        }
						
						if (userAction != null) {
							userToSave.getUserActions().add(userAction);
						}
			
						if(userToSave != null && userAction != null)
						{
							if(userAction.getId() != null) {
								userActionRepository.delete(userAction.getId());
							}
							userActionRepository.save(userAction);
							userRepository.save(userToSave);
	
						}
					}
				}
				
			}catch(Exception exp)
			{
				throw new PlatformException("Exception saving user ", exp);
			}
			return userTO;
		}
		
		@Override
		public UserTO deleteUser(String userId) throws PlatformException 
		{
			try {
				
				if(userId != null) {
					userRepository.delete(userId);
				}
			}catch(Exception exp)
			{
				throw new PlatformException("Exception delete user ", exp);
			}
			
			return null;

			
		}
		
		
		@Override
		public UserTO saveLoggedOurUser(UserTO userTO) throws PlatformException {
		    
		    try {
		    	if (userTO != null) {
			        List<User> loggedOutUser = userRepository.findByUserId(userTO.getUserId());
			       
			        
			        User userPojo = loggedOutUser.get(0);
			       
			        
			        UserAction latestUserAction = null;
			        Date latestLoggedInTime = null;
	
			        for (UserAction userAction : userPojo.getUserActions()) {
			            if (userAction != null) {
			                Date loggedInTime = userAction.getLoggedInTime();
			                Date loggedOutTime = userAction.getLoggedOutTime();
	
			                if (loggedInTime != null && (loggedOutTime == null || loggedOutTime.before(loggedInTime))) {
			                    long oneHourAgoMillis = System.currentTimeMillis() - (60 * 60 * 1000);
			                    Date oneHourAgo = new Date(oneHourAgoMillis);
	
			                    if ((latestLoggedInTime == null || loggedInTime.after(latestLoggedInTime))) {
			                        latestUserAction = userAction;
			                        latestLoggedInTime = loggedInTime;
			                    }
			                }
			            }
			        }
	
			        if (latestUserAction != null) {
			            latestUserAction.setLoggedOutTime(new Date());
			            userActionRepository.save(latestUserAction);
			        } else {
	
			        }
	
			        userRepository.save(userPojo);
		    	}

		    } catch (Exception exp) {
	            System.out.println(" exp ="+ exp.toString());

		        throw new PlatformException("Exception saving user", exp);
		    }

		    return userTO;
		}

		
		@Override
		public List<UserTO> getUsers() throws PlatformException
		{
			List<UserTO> userTOs = new ArrayList<UserTO>();
			
			try
			{
				List<User> userPojos = new ArrayList<User>();
				userRepository.findAll().forEach(user -> userPojos.add(user));
				if(userPojos != null)
				{
					userTOs = AccessConverters.usersPojoToTO(userPojos);
				}
			}catch(Exception exp)
			{
				throw new PlatformException("Exception listing all Users ", exp);
			}
			
			return userTOs;
		}
		
		
		
		
		


		
		
}
