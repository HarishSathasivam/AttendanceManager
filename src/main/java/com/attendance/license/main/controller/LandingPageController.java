package com.nusyn.license.main.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nusyn.license.communication.sheduler.LogedInUser;
import com.nusyn.license.communication.sheduler.SheduleJob;
import com.nusyn.license.communication.sheduler.SheduleManager;
import com.nusyn.license.exception.PlatformException;
import com.nusyn.license.main.access.model.UserActionTO;
import com.nusyn.license.main.access.model.UserTO;
import com.nusyn.license.main.access.pojo.UserAction;
import com.nusyn.license.main.access.service.AccessService;
import com.nusyn.license.manager.LicenseDomainManager;
import com.nusyn.license.manager.NusynUserManager;
import com.nusyn.license.security.HttpSecurity;
import com.nusyn.license.security.URLSecurityCheckResult;
import com.nusyn.license.util.AdminCredentials;
import com.nusyn.license.util.ApplicationStatus;
import com.nusyn.license.util.NusynConstants;
import com.nusyn.license.util.UniqueIdGenerator;
import com.nusyn.license.util.NusynConstants.CurrentApplicationStatus;




@Controller  
public class LandingPageController 
{
	protected static Logger logger = Logger.getLogger(LandingPageController.class.getName());


	@Autowired
	private AccessService accessService;
	
    private final ApplicationContext context = null;

	@GetMapping(value = "rr")
	public String forward(){
		return"forward:/";
	}

	@GetMapping("/logIn")
	public @ResponseBody  String logoIn(HttpServletRequest request,@RequestParam("userId") String userId) {
		 
		try
		{


			if (ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.CANNOT_START) {
	            String errorMessage = ApplicationStatus.getInstance().getStartupErrorMessagesForDisplay();
	            request.setAttribute("ErrorMessage", errorMessage);
	            
	            ObjectMapper objectMapper = new ObjectMapper();
	            String userJsonObjectString = objectMapper.writeValueAsString(errorMessage);
	            return userJsonObjectString;
	            
	            
	        }else 
			{

	        	Date dateNow = new Date();
	        	UserActionTO userActionTO = new UserActionTO();
	        	
	        	UserTO userTOEdit = accessService.getUserByUserId(userId,false);
	        		      
	        	if(userTOEdit != null) {
		        	if ((!"administrator".equals(userTOEdit.getUserName()) && !"Administrator".equals(userTOEdit.getUserName())) || 
				    	    (!"admin".equals(userTOEdit.getUserRole()) && !"admin".equals(userTOEdit.getUserRole()))) {
		        	
			        	  if (userTOEdit.getUserAction() == null) {
			        		  userTOEdit.setUserAction(new HashSet<>());
					       }
			       
			        	  userActionTO.setLoggdInTime(dateNow);
			        	  userActionTO.setLoggedOutTime(null);
			        	  userActionTO.setUser(userTOEdit);
				        	
			        	  userTOEdit.getUserAction().add(userActionTO);	
			        

					      UserTO savedUserTO = accessService.saveUser(userTOEdit,false,userActionTO);
			
			    	       SheduleJob.start(savedUserTO,dateNow);
			    	       ApplicationStatus.getInstance().setCurrentStatus(CurrentApplicationStatus.LANDING_PAGE);
					    	    	
					       return "landing";
					   }
				    
		        	}
			  }
			
			

			
		}catch(Exception exp)
		{
			logger.error("Exception accessing application",exp);
			logger.error("Failed to log out: Unknown error. is ",exp);
			//request.setAttribute("ErrorMessage", "Failed to log out: Unknown error is " + exp.getMessage());
			//	return "errorpage";
			
		}
		return null;
	 
		 
    }	    
	
	
	
	
	@GetMapping("/logOut")
	public @ResponseBody  String logddedOut(HttpServletRequest request,@RequestParam("userId") String userId) {
		 
		try
		{

			if (ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.CANNOT_START) {
	            String errorMessage = ApplicationStatus.getInstance().getStartupErrorMessagesForDisplay();
	            request.setAttribute("ErrorMessage", errorMessage);
	            
	            ObjectMapper objectMapper = new ObjectMapper();
	            String userJsonObjectString = objectMapper.writeValueAsString(errorMessage);
	            return userJsonObjectString;
	            
	            
	        }else{
	        		        	    
	        	UserTO userTOEdit = accessService.getUserByUserId(userId,false);
	        	UserTO savedUserTO = accessService.saveLoggedOurUser(userTOEdit);
	        	
	        	List<LogedInUser> logedInUsers = ApplicationStatus.getInstance().getLogedInUser();

	        	Iterator<LogedInUser> iterator = logedInUsers.iterator();

	        	while (iterator.hasNext()) {
	        	    LogedInUser logedInUser = iterator.next();
	        	    if (logedInUser.getId().equals(userId)) {
	        	        if (userId.equals(logedInUser.getId())) { 
	        	            iterator.remove(); 
	        	        }

	        	        return logedInUser.getStatus().toString();
	        	    }
				}
	        }
		}catch(Exception exp)
		{
			logger.error("Exception accessing application",exp);
			logger.error("Failed to log out: Unknown error. is ",exp);
			
		}
		return null;
		
		
	}
	
	
	
	
	@GetMapping("/addUser")
	public @ResponseBody  String addUser(HttpServletRequest request,@RequestParam("userName") String userName,
            @RequestParam("passWord") String password,@RequestParam("role") String role,@RequestParam("frequencyInterval") Integer frequencyInterval) {
		 
		try
		{

			if (ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.CANNOT_START) {
	            String errorMessage = ApplicationStatus.getInstance().getStartupErrorMessagesForDisplay();
	            request.setAttribute("ErrorMessage", errorMessage);
	            
	            ObjectMapper objectMapper = new ObjectMapper();
	            String userJsonObjectString = objectMapper.writeValueAsString(errorMessage);
	            return userJsonObjectString;
	            
	            
	        }else
			{
				String userId = UniqueIdGenerator.getUUID();
				Date dateNow = new Date();
				
				UserTO userTo = new UserTO();
				userTo.setUserId(userId);
				userTo.setUserName(userName);
				userTo.setPassword(password);
				userTo.setUserRole(role);
				userTo.setUserAction(null);
				userTo.setFrequencyIntervalToLogOut(frequencyInterval);
				UserActionTO userAction = null;
				UserTO savedUserTO = accessService.saveUser(userTo, true,userAction);
	
				ObjectMapper objectMapper = new ObjectMapper();
	            String userJsonObjectString = objectMapper.writeValueAsString("savedUserTO");
	            return userJsonObjectString;
				
			}

			
		}catch(Exception exp)
		{
			logger.error("Exception accessing application",exp);
			logger.error("Failed to log out: Unknown error. is ",exp);
			//request.setAttribute("ErrorMessage", "Failed to log out: Unknown error is " + exp.getMessage());
			//	return "errorpage";
			
		}
		return null;
	 
		 
    }
	
	@RequestMapping(value = "/checkPage", method = RequestMethod.GET)
	public @ResponseBody  String checkPage(HttpServletRequest request,@RequestParam("userId") String userId) throws Exception {
		 
		try
		{
			
			if (ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.CANNOT_START) {
	            String errorMessage = ApplicationStatus.getInstance().getStartupErrorMessagesForDisplay();
	            request.setAttribute("ErrorMessage", errorMessage);
	            
	            ObjectMapper objectMapper = new ObjectMapper();
	            String userJsonObjectString = objectMapper.writeValueAsString(errorMessage);
	            return userJsonObjectString;
	            
	            
	        }else
			{	        	
	        	List<LogedInUser> logedInUsers = ApplicationStatus.getInstance().getLogedInUser();
	        	System.out.println("ApplicationStatus.getInstance().getLogedInUser() size = "+ApplicationStatus.getInstance().getLogedInUser().size());
	        	Iterator<LogedInUser> iterator = logedInUsers.iterator();

	        	while (iterator.hasNext()) {
	        	    LogedInUser logedInUser = iterator.next();
	        	    if (logedInUser.getId().equals(userId)) {
	        	        if ("loggedOut".equals(logedInUser.getStatus())) { // Use .equals() for string comparison
	        	            UserTO userTOEdit = accessService.getUserByUserId(userId, false);
	        	            UserTO savedUserTO = accessService.saveLoggedOurUser(userTOEdit);

	        	            iterator.remove(); // Remove the logged-out user from the list
	        	        }

	        	        return logedInUser.getStatus().toString();
	        	    }
				}

	        	
			  
	            return null;
			}

		}catch(Exception exp)
		{
			logger.error("Failed to log out: Unknown error. is ",exp);
			request.setAttribute("ErrorMessage", "Failed to log out: Unknown error is " + exp.getMessage());
			return "errorpage";
		}
	}
	
	
	
	@GetMapping("/allUsers")
    public ResponseEntity<List<UserTO>> allUsers() throws PlatformException {
       List<UserTO> usersTo = accessService.getUsers();
       return new ResponseEntity<>(usersTo, HttpStatus.OK);
	 
   }
	
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public @ResponseBody  String deleteUser(HttpServletRequest request,@RequestParam("userId") String userId) throws Exception {
		 
		try
		{	
		
			if (ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.CANNOT_START) {
	            String errorMessage = ApplicationStatus.getInstance().getStartupErrorMessagesForDisplay();
	            request.setAttribute("ErrorMessage", errorMessage);
	            
	            ObjectMapper objectMapper = new ObjectMapper();
	            String userJsonObjectString = objectMapper.writeValueAsString(errorMessage);
	            return userJsonObjectString;
	            	            
	        }else
			{	
	        	
	        	UserTO userTODelete = accessService.deleteUser(userId);			    
	            return userId;				
			}

		}catch(Exception exp)
		{
			logger.error("Failed to log out: Unknown error. is ",exp);
			request.setAttribute("ErrorMessage", "Failed to log out: Unknown error is " + exp.getMessage());
			return "errorpage";
		}
	}
	
	
	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public @ResponseBody  String editUser(HttpServletRequest request,@RequestParam("userId") String userId,
			@RequestParam("userId") String id,@RequestParam("userName") String userName,
			@RequestParam("passWord") String passWord, 
			@RequestParam("role") String role,
			@RequestParam("frequencyInterval") Integer frequencyInterval) throws Exception {
		 
		try
		{	
		
			if (ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.CANNOT_START) {
	            String errorMessage = ApplicationStatus.getInstance().getStartupErrorMessagesForDisplay();
	            request.setAttribute("ErrorMessage", errorMessage);
	            
	            ObjectMapper objectMapper = new ObjectMapper();
	            String userJsonObjectString = objectMapper.writeValueAsString(errorMessage);
	            return userJsonObjectString;
	            	            
	        }else
			{	

	        	UserTO userTOEdit = accessService.getUserByUserId(id ,false);
	        	
	    
	        	userTOEdit.setUserName(userName);
	        	userTOEdit.setPassword(passWord);
	        	userTOEdit.setFrequencyIntervalToLogOut(frequencyInterval);
	        	userTOEdit.setUserRole(role);
	        	
				UserTO savedUserTO = accessService.saveUser(userTOEdit, true,null);

	        	

			}

		}catch(Exception exp)
		{
			logger.error("Failed to log out: Unknown error. is ",exp);
			request.setAttribute("ErrorMessage", "Failed to log out: Unknown error is " + exp.getMessage());
			return "errorpage";
		}
		return null;
	}
	
	
	@RequestMapping("/validateUser.htm")
	public String validateUser(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam String username,@RequestParam String password)
			throws Exception 
	{
		try
		{

			//Check if app is ready
			if(ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.CANNOT_START)
			{
				request.setAttribute("ErrorMessage", ApplicationStatus.getInstance().getStartupErrorMessagesForDisplay());
				return "license/errorpage";
			}else if(ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.SHOW_ADMIN_PASSWORD_PAGE)
			{
				return "license/adminpasswordpage";
			}else
			{
				if((username == null) || (username.length() == 0) || (password == null) || (password.length() == 0))
				{
					//Check if session exists
					if(request.getSession().getAttribute("CurrentUser") == null)
					{
						return "loginpage";
					}
				}
				
				try
				{
					

					UserTO loggedInUser = NusynUserManager.validateUser(username, password, accessService);
					
					//Check if there is a preferred network. Else create one.
					//If this user is admin then make sure his access group is the topmost node of the tree
					
					
					request.getSession().setAttribute("CurrentUser", loggedInUser);
						

					if(loggedInUser.showFeature(NusynConstants.Feature.WEB_ACCESS.name()))
					{
						return "forward:/homelandingpage.htm";
						
						
					}else
					{
						request.setAttribute("ErrorMessage", "This User does not have access to the Web Interface.");
						return "loginpage";
					}
				
				 
				}catch(SecurityException exp)
				{
					logger.error("Exception validating user ", exp);
					request.setAttribute("ErrorMessage", exp.getMessage());
					return "loginpage";
				}
			}
		}catch(Exception exp)
		{
			logger.error("Exception validating user " + exp.getMessage(), exp);
			request.setAttribute("ErrorMessage", "Exception authenticating User >> " + exp.getMessage());
			return "errorpage";
		}
	}
	
	
	@RequestMapping("/homelandingpage.htm")
	public String landingPage(HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		try
		{
			
			URLSecurityCheckResult securityResult = HttpSecurity.isUrlSecure(request.getRequestURL().toString() + request.getQueryString());
			
						
			if(!securityResult.isSecure())
			{
				request.setAttribute("ErrorMessage", securityResult.getMessage());
				return "errorpage";
			}
			
			if(ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.CANNOT_START)
			{
				request.setAttribute("ErrorMessage", ApplicationStatus.getInstance().getStartupErrorMessagesForDisplay());
				return "errorpage";
			}else if(ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.SHOW_ADMIN_PASSWORD_PAGE)
			{
				return "adminpasswordpage";
			}else if(request.getSession().getAttribute("CurrentUser") == null)
			{
				//System.out.println("request.getSession().getAttribute(\"CurrentUser\") + " + request.getSession().getAttribute("CurrentUser"));
				return "loginpage";
			}else 
			{
				UserTO currentUser = (UserTO)request.getSession().getAttribute("CurrentUser");
				return "landingpage";
			}
		}catch(Exception exp)
		{
			logger.error("Exception showing landingpage " + exp.getMessage(),exp);
			request.setAttribute("ErrorMessage", "Exception showing landingpage >> " + exp.getMessage());
			return "errorpage";
		}
	}
	
	@RequestMapping("/createAdminPassword.htm")
	public String createAdminPassword(HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		try
		{
			
			
			if(ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.CANNOT_START)
			{
				request.setAttribute("ErrorMessage", ApplicationStatus.getInstance().getStartupErrorMessagesForDisplay());
				return "license/errorpage";
			}else
			{
				String password = request.getParameter("password");
				logger.info("Creating admin password");
				AdminCredentials.getInstance().updateAdminPassword(password);
				AdminCredentials.getInstance().reloadConfig();
				ApplicationStatus.getInstance().setCurrentStatus(CurrentApplicationStatus.STARTED);
				
				UserTO adminUser = new UserTO("administrator",AdminCredentials.getInstance().getAdminPassword());
				LicenseDomainManager.getInstance().verifyAdminUser(accessService, adminUser);
				
				return "redirect:/homelandingpage.htm";
			}
		}catch(Exception exp)
		{
			logger.error("Exception creating admin password " + exp.getMessage(),exp);
			request.setAttribute("ErrorMessage", exp.getMessage());
			return "adminpasswordpage";
		}
	}
	
	
	@RequestMapping("/licenselandingpage.htm")
	public String licenselandingPage(HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		try
		{
		
			URLSecurityCheckResult securityResult = HttpSecurity.isUrlSecure(request.getRequestURL().toString() + request.getQueryString());
			if(!securityResult.isSecure())
			{
				request.setAttribute("ErrorMessage", securityResult.getMessage());
				return "errorpage";
			}
			
			if(ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.CANNOT_START)
			{
				request.setAttribute("ErrorMessage", ApplicationStatus.getInstance().getStartupErrorMessagesForDisplay());
				return "errorpage";
			}else if(ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.SHOW_ADMIN_PASSWORD_PAGE)
			{
				return "adminpasswordpage";
			}else if(request.getSession().getAttribute("CurrentUser") == null)
			{
				return "loginpage";
			}else 
			{
				UserTO currentUser = (UserTO)request.getSession().getAttribute("CurrentUser");
				return "licenselandingpage";
			}
		}catch(Exception exp)
		{
			logger.error("Exception showing landingpage " + exp.getMessage(),exp);
			//request.setAttribute("ErrorMessage", "Exception showing landingpage >> " + exp.getMessage());
			return "errorpage";
		}
	}
	
	@RequestMapping("/logoutpage.htm")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		try
		{
			UserTO CurrentUser = (UserTO)request.getSession().getAttribute("CurrentUser");
			if(CurrentUser != null)
			{
				CurrentUser.setUserId(null);
			}
			request.getSession().setAttribute("CurrentUser", null);
			request.getSession().setAttribute("Permissions", null);
			return "loginpage";
		}catch(Exception exp)
		{
			logger.error("Failed to log out: Unknown error. is ",exp);
			request.setAttribute("ErrorMessage", "Failed to log out: Unknown error is " + exp.getMessage());
			return "errorpage";
		}
	}
	
	@RequestMapping("/projectlandingpage.htm")
	public String projectlandingpage(HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		try
		{
		
			URLSecurityCheckResult securityResult = HttpSecurity.isUrlSecure(request.getRequestURL().toString() + request.getQueryString());
			if(!securityResult.isSecure())
			{
				request.setAttribute("ErrorMessage", securityResult.getMessage());
				return "errorpage";
			}
			
			if(ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.CANNOT_START)
			{
				request.setAttribute("ErrorMessage", ApplicationStatus.getInstance().getStartupErrorMessagesForDisplay());
				return "errorpage";
			}else if(ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.SHOW_ADMIN_PASSWORD_PAGE)
			{
				return "adminpasswordpage";
			}else if(request.getSession().getAttribute("CurrentUser") == null)
			{
				return "loginpage";
			}else 
			{
				UserTO currentUser = (UserTO)request.getSession().getAttribute("CurrentUser");
				return "projectPage";
			}
		}catch(Exception exp)
		{
			logger.error("Exception showing landingpage " + exp.getMessage(),exp);
			//request.setAttribute("ErrorMessage", "Exception showing landingpage >> " + exp.getMessage());
			return "errorpage";
		}
	}
	
	
	@RequestMapping("/setupLandingPage.htm")
	public String setupLandingPage(HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		try
		{
		
			URLSecurityCheckResult securityResult = HttpSecurity.isUrlSecure(request.getRequestURL().toString() + request.getQueryString());
			if(!securityResult.isSecure())
			{
				request.setAttribute("ErrorMessage", securityResult.getMessage());
				return "errorpage";
			}
			
			if(ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.CANNOT_START)
			{
				request.setAttribute("ErrorMessage", ApplicationStatus.getInstance().getStartupErrorMessagesForDisplay());
				return "errorpage";
			}else if(ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.SHOW_ADMIN_PASSWORD_PAGE)
			{
				return "adminpasswordpage";
			}else if(request.getSession().getAttribute("CurrentUser") == null)
			{
				return "loginpage";
			}else 
			{
				UserTO currentUser = (UserTO)request.getSession().getAttribute("CurrentUser");
				return "setupPage";
			}
		}catch(Exception exp)
		{
			logger.error("Exception showing landingpage " + exp.getMessage(),exp);
			//request.setAttribute("ErrorMessage", "Exception showing landingpage >> " + exp.getMessage());
			return "errorpage";
		}
	}
	
	
	@RequestMapping("/projectGroupPage.htm")
	public String projectGroupPage(HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		try
		{
		
			URLSecurityCheckResult securityResult = HttpSecurity.isUrlSecure(request.getRequestURL().toString() + request.getQueryString());
			if(!securityResult.isSecure())
			{
				request.setAttribute("ErrorMessage", securityResult.getMessage());
				return "errorpage";
			}
			
			if(ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.CANNOT_START)
			{
				request.setAttribute("ErrorMessage", ApplicationStatus.getInstance().getStartupErrorMessagesForDisplay());
				return "errorpage";
			}else if(ApplicationStatus.getInstance().getCurrentStatus() == CurrentApplicationStatus.SHOW_ADMIN_PASSWORD_PAGE)
			{
				return "adminpasswordpage";
			}else if(request.getSession().getAttribute("CurrentUser") == null)
			{
				return "loginpage";
			}else 
			{
				//System.out.println("controller licenselandingpage.htm");
				UserTO currentUser = (UserTO)request.getSession().getAttribute("CurrentUser");
				return "projectGroupPage";
			}
		}catch(Exception exp)
		{
			logger.error("Exception showing landingpage " + exp.getMessage(),exp);
			//request.setAttribute("ErrorMessage", "Exception showing landingpage >> " + exp.getMessage());
			return "errorpage";
		}
	}
	
}
