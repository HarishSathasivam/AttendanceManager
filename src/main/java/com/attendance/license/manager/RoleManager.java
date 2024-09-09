package com.nusyn.license.manager;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.nusyn.license.main.access.model.AccessTO;
import com.nusyn.license.main.access.model.RoleTO;
import com.nusyn.license.main.access.service.AccessService;



public class RoleManager {

	protected static Logger logger = Logger.getLogger(RoleManager.class.getName());
	
	private static RoleManager instance = null;
	
	private RoleManager()
	{
		
	}
	
	public static RoleManager getInstance()
	{
		if(instance == null)
		{
			instance = new RoleManager();
		}
		return instance;
	}

	public void verifyRole(String roleName, Set<AccessTO> accessRights,AccessService accessService, String domain) throws Exception
	{
		try
		{
			List<RoleTO> matchingRoles = accessService.getRolesByName(roleName, true);
			if((matchingRoles == null) || (matchingRoles.size() == 0))
			{
				//Create the role
				RoleTO role = new RoleTO();
				role.setName(roleName);
				role.setDomain(domain);
				role.setAccesses(accessRights);
				accessService.saveRole(role);
			}else
			{
				//make sure access is set right
				RoleTO role = matchingRoles.get(0);//Expect only one
				Hashtable<String,AccessTO> existingAccessMap = convertAccessToMap(role.getAccesses());
				//Check if all accesses exist
				Iterator<AccessTO> accessItr = accessRights.iterator();
				while(accessItr.hasNext())
				{
					AccessTO access = accessItr.next();
					updateAccessRight(existingAccessMap, access.getFeature(), access.getLevel());
				}
				Set<AccessTO> modifiedAccessRights = convertAccessToSet(existingAccessMap);
				role.setDomain(domain);
				role.setAccesses(modifiedAccessRights);
				role = accessService.saveRole(role);
			}
		}catch(Exception exp)
		{
			logger.error("Exception checking role " + roleName,exp);
			throw exp;
		}
	}
	
	private Hashtable<String, AccessTO> updateAccessRight(Hashtable<String, AccessTO> accessMap, String featureName, String accessLevel)
	{
		if(accessMap.get(featureName) == null)
		{
			AccessTO access = new AccessTO(featureName,accessLevel);
			accessMap.put(featureName,access);
		}else
		{
			//Make sure the access type is correct
			AccessTO currentAccess = accessMap.get(featureName);
			if(currentAccess.getLevel().compareTo(accessLevel) != 0)
			{
				currentAccess.setLevel(accessLevel);
				accessMap.put(featureName,currentAccess);
			}
		}
		
		return accessMap;
	}
	
	private Hashtable<String,AccessTO> convertAccessToMap(Set<AccessTO> accessRights)
	{
		Hashtable<String, AccessTO> accessMap = new Hashtable<String, AccessTO>();
		if(accessRights != null)
		{
			Iterator<AccessTO> accessItr = accessRights.iterator();
			while(accessItr.hasNext())
			{
				AccessTO access = accessItr.next();
				
				accessMap.put(access.getFeature(), access);
			}
		}
		return accessMap;
	}
	private Set<AccessTO> convertAccessToSet(Hashtable<String,AccessTO> accessRights)
	{
		Set<AccessTO> accessSet = new HashSet<AccessTO>();
		if(accessRights != null)
		{
			Iterator<AccessTO> accessItr = accessRights.values().iterator();
			while(accessItr.hasNext())
			{
				AccessTO access = accessItr.next();
				accessSet.add(access);
			}
		}
		return accessSet;
	}
}
