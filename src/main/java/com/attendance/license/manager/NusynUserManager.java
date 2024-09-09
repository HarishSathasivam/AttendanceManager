package com.nusyn.license.manager;

import java.util.List;

import org.apache.log4j.Logger;

import com.nusyn.license.exception.ConfigException;
import com.nusyn.license.main.access.model.RoleTO;
import com.nusyn.license.main.access.model.UserPropertyTO;
import com.nusyn.license.main.access.model.UserTO;
import com.nusyn.license.main.access.service.AccessService;
import com.nusyn.license.util.AdminCredentials;
import com.nusyn.license.util.NusynConstants;

public class NusynUserManager {
	
	protected static Logger logger = Logger.getLogger(NusynUserManager.class.getName());

	public static void verifyUserExists(AccessService accessService, UserTO nusynUser) throws Exception
	{
		try
		{
			//Check if admin user exists
			String filter = "user.userId='" +nusynUser.getUserId() + "'";
			List<UserTO> matchingUsers = accessService.getUsers();	
			if((matchingUsers == null) || (matchingUsers.size() == 0))
			{
				//Admin user does not exist. Create one
				//First add the role
				List<RoleTO> matchingRoles = accessService.getRolesByName(NusynConstants.DefaultRole.ADMINISTRATOR.name(), true);
				RoleTO adminRole = matchingRoles.get(0);
				nusynUser.setRole(adminRole);
				
				//Add properties that tag this as a system user and non editable
				UserPropertyTO systemProperty = new UserPropertyTO(NusynConstants.UserProperty.SYSTEM.getType(),NusynConstants.UserProperty.SYSTEM.getName(),"True");
				UserPropertyTO editableProperty = new UserPropertyTO(NusynConstants.UserProperty.EDITABLE.getType(),NusynConstants.UserProperty.EDITABLE.getName(),"False");
				nusynUser.addProperty(systemProperty);
				nusynUser.addProperty(editableProperty);
				//accessService.saveUser(nusynUser);
			}else
			{
				//All good
			}
		}catch(Exception exp)
		{
			logger.error("Exception checking NusynUuser " + nusynUser,exp);
			throw exp;
		}
	}
	
	public static UserTO validateUser(String userName, String password, AccessService nusynAccessService) throws SecurityException,ConfigException
	{
		UserTO user = null;
		try
		{
			if(userName.compareToIgnoreCase("administrator") == 0)
			{
				AdminCredentials.getInstance().authenticateAdministrator(password);
				user = nusynAccessService.getUserByUserId(userName, true);
			}else
			{

				user = nusynAccessService.getUserByUserId(userName, true);
				if(user == null)
				{
					throw new SecurityException("Cannot find user with username " + userName,null);
				}else
				{
					if(user.getPassword().compareTo(password) == 0)
					{
						//Valid
					}else
					{
						//Not valid
						throw new SecurityException("Invalid username or password ",null);
					}
				}
				return user;
			}
		}catch(SecurityException exp)
		{
			throw exp;
		}catch(ConfigException exp)
		{
			throw exp;
		}catch(Exception exp)
		{
			logger.error("Exception Validating NusynUser ",exp);
			throw new SecurityException("Exception Validating User ",null);
		}
		return user;
	}
}
