package com.nusyn.license.main.access.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import com.nusyn.license.main.access.model.AccessTO;

import com.nusyn.license.main.access.model.RolePropertyTO;
import com.nusyn.license.main.access.model.RoleTO;
import com.nusyn.license.main.access.model.UserActionTO;
import com.nusyn.license.main.access.model.UserPropertyTO;
import com.nusyn.license.main.access.model.UserTO;
import com.nusyn.license.main.access.pojo.Access;

import com.nusyn.license.main.access.pojo.Role;
import com.nusyn.license.main.access.pojo.RoleProperty;
import com.nusyn.license.main.access.pojo.User;
import com.nusyn.license.main.access.pojo.UserAction;





public class AccessConverters {

	//User 
//	public static List<UserTO> userPojoToTO(List<User> allUserPojos, boolean loadDetails)
//	{
//		List<UserTO> allUserTOs = new ArrayList<UserTO>();
//		if((allUserPojos != null) && (allUserPojos.size() > 0))
//		{
//			Iterator<User> usersItr = allUserPojos.iterator();
//			while(usersItr.hasNext())
//			{
//				User userPojo = usersItr.next();
//				UserTO assertTO = pojoToTO(userPojo,loadDetails);
//				allUserTOs.add(assertTO);
//			}
//		}
//		return allUserTOs;
//	}
	
	public static List<UserTO> usersPojoToTO(List<User> allUserPojos)
	{
		List<UserTO> allUserTOs = new ArrayList<UserTO>();
		if((allUserPojos != null) && (allUserPojos.size() > 0))
		{
			Iterator<User> userItr = allUserPojos.iterator();
			while(userItr.hasNext())
			{
				User userPojo = userItr.next();
				UserTO assertTO1 = pOjoToTO(userPojo);
				allUserTOs.add(assertTO1);
			}
		}
		
		return allUserTOs;
	}
	
	
	public static UserTO pOjoToTO(User userPojo)
	{
		if(userPojo != null)
		{
			UserTO userTO = new UserTO();
	
			
			userTO.setUserId(userPojo.getUserId());
			userTO.setPassword(userPojo.getPassword());
			userTO.setUserName(userPojo.getUserName());
			userTO.setLogedInTime(userPojo.getLoggedInTime());
			userTO.setLogedOutTime(userPojo.getLoggedOutTime());
			userTO.setUserRole(userPojo.getUserRole());
			userTO.setFrequencyIntervalToLogOut(userPojo.getFrequencyIntervalToLogOut());
			
			 Set<UserActionTO> userActionTOSet = new HashSet<>();
			 
			 if(userTO.getUserAction() == null) {
				 userTO.setUserAction(new HashSet<>());
		       }

		        for (UserAction userAction : userPojo.getUserActions()) {
		            if (userAction != null) {		              
		            	userTO.getUserAction().add(UserActionToPojo(userAction));
		            }
		        }

		       return userTO;
		}else
		{
			return null;
		}
		
	}
	
	
	
	public static UserActionTO UserActionToPojo(UserAction userAction) {
		
        if (userAction == null) {
            return null;
        }

        
	    UserActionTO userActionTo = new UserActionTO();
	    userActionTo.setLoggdInTime(userAction.getLoggedInTime());
	    userActionTo.setLoggedOutTime(userAction.getLoggedOutTime());
	    
	    return userActionTo;

          
       
        
	}
	
	public static UserTO pojoToTO(User userPojo,boolean loadDetails)
	{
		if(userPojo != null)
		{
			UserTO userTO = new UserTO();
			//userTO.setId(userPojo.getId());
			userTO.setClusterId(userPojo.getClusterId());
			userTO.setUserId(userPojo.getUserId());
			userTO.setPassword(userPojo.getPassword());
			userTO.setUserName(userPojo.getUserName());
			userTO.setLogedInTime(userPojo.getLoggedInTime());
			userTO.setLogedOutTime(userPojo.getLoggedOutTime());
			userTO.setUserRole(userPojo.getUserRole());
			//userTO.setUserAction(userPojo.getUserActions());
		
			

			
			return userTO;
		}else
		{
			return null;
		}
		
	}
	
//	//UserProperty
//		public static Set<UserPropertyTO> userPropertyPojoToTO(Collection<UserProperty> allUserPropertyPojos)
//		{
//			Set<UserPropertyTO> allUserPropertyTOs = new HashSet<UserPropertyTO>();
//			if((allUserPropertyPojos != null) && (allUserPropertyPojos.size() > 0))
//			{
//				Iterator<UserProperty> userPropertiesItr = allUserPropertyPojos.iterator();
//				while(userPropertiesItr.hasNext())
//				{
//					UserProperty userPropertyPojo = userPropertiesItr.next();
//					UserPropertyTO assertVersionPropertyTO = pojoToTO(userPropertyPojo);
//					allUserPropertyTOs.add(assertVersionPropertyTO);
//				}
//			}
//			return allUserPropertyTOs;
//		}
//		
//		
//		
//		public static UserPropertyTO pojoToTO(UserProperty userPropertyPojo)
//		{
//			UserPropertyTO userPropertyTO = new UserPropertyTO();
//			userPropertyTO.setId(userPropertyPojo.getId());
//			userPropertyTO.setType(userPropertyPojo.getType());
//			userPropertyTO.setName(userPropertyPojo.getName());
//			userPropertyTO.setValue(userPropertyPojo.getValue());
//			
//			return userPropertyTO;
//		}
	
	public static UserTO pojoToTO(User userPojo)
	{
		if(userPojo != null)
		{
			UserTO userTO = new UserTO();
			
			return userTO;
		}else
		{
			return null;
		}
		
	}
	
	public static Role TOToPojo(RoleTO roleTO)
	{
		if(roleTO != null)
		{
			Role rolePojo = new Role();
			rolePojo.setId(roleTO.getId());
			rolePojo.setClusterId(roleTO.getClusterId());
			rolePojo.setName(roleTO.getName());
			rolePojo.setDomain(roleTO.getDomain());
			
			Set<RolePropertyTO> rolePropertyTOs = roleTO.getRoleProperties();
			Set<RoleProperty> rolePropertyPojos = rolePropertyTOToPojo(rolePropertyTOs,rolePojo);
			rolePojo.setRoleProperties(rolePropertyPojos);
			
			Set<AccessTO> accessTOs = roleTO.getAccesses();
			Set<Access> accessPojos = accessTOToPojo(accessTOs,rolePojo);
			rolePojo.setAccesses(accessPojos);
			
			return rolePojo;
		}else
		{
			return null;
		}
	}
	
	public static Set<RoleProperty> rolePropertyTOToPojo(Collection<RolePropertyTO> allRolePropertyTOs, Role role)
	{
		Set<RoleProperty> allRolePropertyPojos = new HashSet<RoleProperty>();
		if((allRolePropertyTOs != null) && (allRolePropertyTOs.size() > 0))
		{
			Iterator<RolePropertyTO> userPropertiesItr = allRolePropertyTOs.iterator();
			while(userPropertiesItr.hasNext())
			{
				RolePropertyTO rolePropertyTO = userPropertiesItr.next();
				RoleProperty rolePropertyPojo = TOToPojo(rolePropertyTO,role);
				allRolePropertyPojos.add(rolePropertyPojo);
			}
		}
		return allRolePropertyPojos;
	}
	
	private static RoleProperty TOToPojo(RolePropertyTO rolePropertyTO, Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Set<Access> accessTOToPojo(Set<AccessTO> allAccessTOs, Role role)
	{
		Set<Access> allAccessPojos = new HashSet<Access>();
		if((allAccessTOs != null) && (allAccessTOs.size() > 0))
		{
			Iterator<AccessTO> accesssItr = allAccessTOs.iterator();
			while(accesssItr.hasNext())
			{
				AccessTO accessTO = accesssItr.next();
				Access accessPojo = TOToPojo(accessTO);
				accessPojo.setRole(role);
				allAccessPojos.add(accessPojo);
			}
		}
		return allAccessPojos;
	}
	
	public static Access TOToPojo(AccessTO accessTO)
	{
		if(accessTO != null)
		{
			Access accessPojo = new Access();
			accessPojo.setId(accessTO.getId());
			accessPojo.setFeature(accessTO.getFeature());
			accessPojo.setLevel(accessTO.getLevel());
			
			return accessPojo;
		}else
		{
			return null;
		}
	}

	public static List<RoleTO> rolePojoToTO(List<Role> allRolePojos, boolean loadDetails)
	{
		List<RoleTO> allRoleTOs = new ArrayList<RoleTO>();
		if((allRolePojos != null) && (allRolePojos.size() > 0))
		{
			Iterator<Role> rolesItr = allRolePojos.iterator();
			while(rolesItr.hasNext())
			{
				Role rolePojo = rolesItr.next();
				RoleTO assertTO = pojoToTO(rolePojo,loadDetails);
				allRoleTOs.add(assertTO);
			}
		}
		return allRoleTOs;
	}
	
	public static RoleTO pojoToTO(Role rolePojo,boolean loadDetails)
	{
		if(rolePojo != null)
		{
			RoleTO roleTO = new RoleTO();
			roleTO.setId(rolePojo.getId());
			roleTO.setClusterId(rolePojo.getClusterId());
			roleTO.setName(rolePojo.getName());
			roleTO.setDomain(rolePojo.getDomain());
			
			if( loadDetails	)
			{
				Set<RoleProperty> rolePropertyPojos = rolePojo.getRoleProperties();
				Set<RolePropertyTO> rolePropertyTOs = rolePropertyPojoToTO(rolePropertyPojos);
				roleTO.setRoleProperties(rolePropertyTOs);
				
				Set<Access> accessPojos = rolePojo.getAccesses();
				Set<AccessTO> accessTOs = accessPojoToTO(accessPojos,loadDetails);
				roleTO.setAccesses(accessTOs);
			}
			return roleTO;
		}else
		{
			return null;
		}
		
	}
	
	
	//RoleProperty
		public static Set<RolePropertyTO> rolePropertyPojoToTO(Collection<RoleProperty> allRolePropertyPojos)
		{
			Set<RolePropertyTO> allRolePropertyTOs = new HashSet<RolePropertyTO>();
			if((allRolePropertyPojos != null) && (allRolePropertyPojos.size() > 0))
			{
				Iterator<RoleProperty> userPropertiesItr = allRolePropertyPojos.iterator();
				while(userPropertiesItr.hasNext())
				{
					RoleProperty rolePropertyPojo = userPropertiesItr.next();
					RolePropertyTO assertVersionPropertyTO = pojoToTO(rolePropertyPojo);
					allRolePropertyTOs.add(assertVersionPropertyTO);
				}
			}
			return allRolePropertyTOs;
		}
		
		
		//Access 
		public static Set<AccessTO> accessPojoToTO(Set<Access> allAccessPojos, boolean loadDetails)
		{
			Set<AccessTO> allAccessTOs = new HashSet<AccessTO>();
			if((allAccessPojos != null) && (allAccessPojos.size() > 0))
			{
				Iterator<Access> accesssItr = allAccessPojos.iterator();
				while(accesssItr.hasNext())
				{
					Access accessPojo = accesssItr.next();
					AccessTO assertTO = pojoToTO(accessPojo,loadDetails);
					allAccessTOs.add(assertTO);
				}
			}
			return allAccessTOs;
		}
		
		
		public static RolePropertyTO pojoToTO(RoleProperty rolePropertyPojo)
		{
			RolePropertyTO rolePropertyTO = new RolePropertyTO();
			rolePropertyTO.setId(rolePropertyPojo.getId());
			rolePropertyTO.setType(rolePropertyPojo.getType());
			rolePropertyTO.setName(rolePropertyPojo.getName());
			rolePropertyTO.setValue(rolePropertyPojo.getValue());
			
			return rolePropertyTO;
		}
		
		public static AccessTO pojoToTO(Access accessPojo,boolean loadDetails)
		{
			if(accessPojo != null)
			{
				AccessTO accessTO = new AccessTO();
				accessTO.setId(accessPojo.getId());
				accessTO.setFeature(accessPojo.getFeature());
				accessTO.setLevel(accessPojo.getLevel());
				
				return accessTO;
			}else
			{
				return null;
			}
			
		}
		
		
		public static User TOToPojo(UserTO userTO)
		{
			if(userTO != null)
			{
				User userPojo = new User();
			    userPojo.setUserId(userTO.getUserId());
				userPojo.setPassword(userTO.getPassword());
				userPojo.setUserName(userTO.getUserName());
				userPojo.setUserRole(userTO.getUserRole());
				userPojo.setLoggedInTime(userTO.getLogedInTime());
				userPojo.setFrequencyIntervalToLogOut(userTO.getFrequencyIntervalToLogOut());
				
				return userPojo;
			}else
			{
				return null;
			}
		}
		
		
//		public static Set<UserProperty> userPropertyTOToPojo(Collection<UserPropertyTO> allUserPropertyTOs, User user)
//		{
//			Set<UserProperty> allUserPropertyPojos = new HashSet<UserProperty>();
//			if((allUserPropertyTOs != null) && (allUserPropertyTOs.size() > 0))
//			{
//				Iterator<UserPropertyTO> userPropertiesItr = allUserPropertyTOs.iterator();
//				while(userPropertiesItr.hasNext())
//				{
//					UserPropertyTO userPropertyTO = userPropertiesItr.next();
//					UserProperty userPropertyPojo = TOToPojo(userPropertyTO,user);
//					allUserPropertyPojos.add(userPropertyPojo);
//				}
//			}
//			return allUserPropertyPojos;
//		}
//		
//		public static UserProperty TOToPojo(UserPropertyTO userPropertyTO, User user)
//		{
//			UserProperty userPropertyPojo = new UserProperty();
//			userPropertyPojo.setId(userPropertyTO.getId());
//			userPropertyPojo.setType(userPropertyTO.getType());
//			userPropertyPojo.setName(userPropertyTO.getName());
//			userPropertyPojo.setValue(userPropertyTO.getValue());
//			userPropertyPojo.setUser(user);
//			
//			return userPropertyPojo;
//		}
		
		

		public static UserAction UserActionTOToPojo(UserActionTO userActionTo) {
		
	        if (userActionTo == null) {
	            return null;
	        }

	        Set<UserAction> userActionSet = new HashSet<>();

	     
            UserAction userAction = new UserAction();
            userAction.setLoggedInTime(userActionTo.getLoggdInTime());
            userAction.setLoggedOutTime(userActionTo.getLoggedOutTime());
	        return userAction;

	            
	     
	        
		}
		
		public static Set<UserAction> UserActionToToPojo(Set<UserActionTO> userActionTO) {
		
	        if (userActionTO == null) {
	            return null;
	        }

	        Set<UserAction> userActionSet = new HashSet<>();
	        
	        UserAction userAction = new UserAction();
	  
	        for (UserActionTO userActionTo : userActionTO) {
		            if (userActionTo != null) {		              
		            	userActionSet.add(UserActionPojoToTO(userActionTo));
		            }
		        };
		   
	        return userActionSet;

	        
		}

		public static UserAction UserActionPojoToTO(UserActionTO userActionto) {
			
	        if (userActionto == null) {
	            return null;
	        }
	        
	        UserAction userAction = new UserAction();
	        userAction.setLoggedInTime(userActionto.getLoggdInTime());
	        userAction.setLoggedOutTime(userActionto.getLoggedOutTime());
		    
		    return userAction;
   
		}

}
	
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
		
		
		

		



