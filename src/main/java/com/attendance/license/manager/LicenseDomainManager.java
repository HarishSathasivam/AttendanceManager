package com.nusyn.license.manager;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.nusyn.license.exception.StartupException;
import com.nusyn.license.main.access.model.AccessTO;
import com.nusyn.license.main.access.model.UserTO;
import com.nusyn.license.main.access.service.AccessService;
import com.nusyn.license.util.ApplicationStatus;
import com.nusyn.license.util.NusynConstants;

public class LicenseDomainManager{

protected static Logger logger = Logger.getLogger(LicenseDomainManager.class.getName());
	
	private static LicenseDomainManager instance = null;
	
	private LicenseDomainManager()
	{
		
	}
	public static LicenseDomainManager getInstance()
	{
		if(instance == null)
		{
			instance = new LicenseDomainManager();
		}
		return instance;
	}

		
	public void checkProjectFolders()
	{
		//Check project folder
		try
		{
			FolderManager.getInstance().checkFolder(NusynConstants.productFolderPath);
		}catch(StartupException exp)
		{
			ApplicationStatus.getInstance().addErrorMessage("Exception checking product folder status " + NusynConstants.productFolderPath + " >> " + exp.getMessage());
		}
		
		//Check data folder
		try
		{
			FolderManager.getInstance().checkFolder(NusynConstants.dataFolderPath);
		}catch(StartupException exp)
		{
			ApplicationStatus.getInstance().addErrorMessage("Exception checking data folder status " + NusynConstants.dataFolderPath + " >> " + exp.getMessage());
		}
	}
	
	public void verifyAdminUser(AccessService accessService, UserTO nusynUser) throws Exception
	{
		try
		{
			NusynUserManager.verifyUserExists(accessService, nusynUser);
		}catch(Exception exp)
		{
			logger.error("Exception creating default users ",exp);
			throw exp;
		}
	}
	
	public void initializeRoles(AccessService accessService)
	{
		//Make sure all license specific roles are set
		RoleManager rolesManager = RoleManager.getInstance();
		try
		{
			//ADMINISTRATOR
			{
				Set<AccessTO> accessRights = new HashSet<AccessTO>();
				accessRights.add(new AccessTO(NusynConstants.LicenseFeature.WEB_ACCESS.name(),NusynConstants.AccessLevel.FULLACCESS.name()));
				accessRights.add(new AccessTO(NusynConstants.LicenseFeature.SETUP.name(),NusynConstants.AccessLevel.FULLACCESS.name()));
				
				rolesManager.verifyRole(NusynConstants.DefaultRole.ADMINISTRATOR.name(),accessRights,accessService,NusynConstants.ROLE_DOMAIN_NAME);
			}
			
			//LicenseManager
			{
				Set<AccessTO> accessRights = new HashSet<AccessTO>();
				accessRights.add(new AccessTO(NusynConstants.LicenseFeature.WEB_ACCESS.name(),NusynConstants.AccessLevel.FULLACCESS.name()));
				accessRights.add(new AccessTO(NusynConstants.LicenseFeature.SETUP.name(),NusynConstants.AccessLevel.NOACCESS.name()));
				rolesManager.verifyRole(NusynConstants.LicenseRole.LICENSE_MANAGER.name(),accessRights,accessService,NusynConstants.ROLE_DOMAIN_NAME);
			}
					}catch(Exception exp)
		{
			logger.error("Exception checking roles ",exp);
		}
	}
}
