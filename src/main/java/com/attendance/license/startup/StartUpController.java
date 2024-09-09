package com.nusyn.license.startup;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.nusyn.license.communication.websocket.WebSocketManager;
import com.nusyn.license.main.access.model.UserTO;
import com.nusyn.license.main.access.service.AccessService;
import com.nusyn.license.manager.LicenseDomainManager;
import com.nusyn.license.manager.ServletContextManager;
import com.nusyn.license.manager.SpringContextManager;
import com.nusyn.license.util.AdminCredentials;
import com.nusyn.license.util.ApplicationStatus;
import com.nusyn.license.util.NusynConstants;
import com.nusyn.license.util.NusynConstants.CurrentApplicationStatus;


@Component
public class StartUpController implements ApplicationContextAware, ServletContextAware{
	protected static Logger logger = Logger.getLogger(StartUpController.class.getName());
	
	private static ApplicationContext CONTEXT;
	
	private ServletContext servletContext;
	
	private String applicationName = "NusynLicenseManager";
	
	@Autowired
	private AccessService accessService;
	
	public void setApplicationContext(ApplicationContext context) throws BeansException {
	    CONTEXT = context;
	  }
	
	public void setServletContext(ServletContext servletContext) {
	     this.servletContext = servletContext;
	}
	
	public static Object getBean(String beanName) {
	    return CONTEXT.getBean(beanName);
	  }
	
	 

	@PostConstruct
	public void start() 
	{
		logger.info("@@@@@@@@@@@Startup >> Starting up " + applicationName);
		//Set the Spring COntext ibnto the SpringContextManager
		SpringContextManager.getInstance().setApplicationContext(CONTEXT);
		//Set the ServletContext into the ServletContextManager
		ServletContextManager.getInstance().setServletContext(servletContext);
		
		NusynConstants.setApplicationParameters(applicationName,NusynConstants.javaScriptVersion,NusynConstants.imageVersion,NusynConstants.cssVersion,"licensecontent");
		ApplicationStatus.getInstance().clearErrors();
		
		ApplicationStatus.getInstance().setCurrentStatus(CurrentApplicationStatus.STARTING_UP);
		
		try
		{
			//Make sure Project folders are generated
			LicenseDomainManager.getInstance().checkProjectFolders();
			
			//Configure default roles
			LicenseDomainManager.getInstance().initializeRoles(accessService);
			
			String bootupFilePath = NusynConstants.productFolderPath + File.separator + "Config" + File.separator + NusynConstants.bootupFileName;
			ConfigReaderUtil.loadConfig(bootupFilePath);
			ApplicationStatus.getInstance().start();
			ApplicationStatus.getInstance().setCurrentStatus(CurrentApplicationStatus.STARTED);
			WebSocketManager.getInstance().init();
			
			//Load admin credentials
			AdminCredentials adminCredentials = AdminCredentials.getInstance();

			if(!adminCredentials.isConfigured())
			{
				ApplicationStatus.getInstance().setCurrentStatus(CurrentApplicationStatus.SHOW_ADMIN_PASSWORD_PAGE);
			}else
			{
				logger.info("Admin Credentials loaded ");
				ApplicationStatus.getInstance().setCurrentStatus(CurrentApplicationStatus.STARTED);
				//Make sure administrator user is there in the database
				try
				{
					UserTO adminUser = new UserTO("administrator",adminCredentials.getAdminPassword());
					LicenseDomainManager.getInstance().verifyAdminUser(accessService, adminUser);
				}catch(Exception exp)
				{
					logger.error("Exception creating administrator user " + exp.getMessage(),exp);
					ApplicationStatus.getInstance().addErrorMessage("Exception creating administrator user >> " + exp.getMessage());
				}
			}
		}catch(Exception exp){
			ApplicationStatus.getInstance().setCurrentStatus(CurrentApplicationStatus.CANNOT_START);
			logger.error(exp.getMessage(), exp );
			ApplicationStatus.getInstance().addErrorMessage("Exception while startup " + exp.getMessage());
			exp.printStackTrace();
		}
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
}
