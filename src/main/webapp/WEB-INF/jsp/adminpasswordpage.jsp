<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.nusyn.license.util.NusynConstants"%>
<html>
<head>
<link rel="shortcut icon" href="image/nusyn_favicon.png?<%= NusynConstants.imageVersion %>"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/master.css?<%= NusynConstants.cssVersion %>">
<link rel="stylesheet" type="text/css" href="css/nusyn_main.css?1<%= NusynConstants.cssVersion %>">
<link rel="stylesheet" type="text/css" href="css/login.css?<%= NusynConstants.cssVersion %>">
<link rel="stylesheet" type="text/css" href="css/jquery-ui.theme.css?<%= NusynConstants.cssVersion %>">

<script type="text/javascript" src="js/jquery-1.11.1.min.js?<%= NusynConstants.javaScriptVersion %>"></script>
<script type="text/javascript" src="js/jquery-ui.js?<%= NusynConstants.javaScriptVersion %>"></script>
<title><%= NusynConstants.defaultPageTitle %></title>
<script type="text/javascript">
	function generateNewAdminPassword()
	{
		if($("#input_password").val() == "")
		{
			alert("Please provide a valid password.");
		}else
		{
			var formToSubmit = $("#loginForm");
			//formToSubmit.attr("action", "createAdminPassword.htm?password=" + $("#input_password").val());
			formToSubmit.append("<input type='hidden' name='password' value='"+ $("#input_password").val() +"' />");
			formToSubmit.attr("action", "createAdminPassword.htm");
			formToSubmit.submit();
		}
	}
	$(document).ready(function() {
		$(document).ready(function() {
			//Position layout items
			$( "button" ).button();
		});
	});
</script>
</head>
<body style="width:100%;height:100%;background-image: url('image/nusyn_bg_tile.png?<%= NusynConstants.imageVersion %>')">
	<% 
	response.setHeader("Cache-Control","no-cache");  //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
	response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
	String sessionid=(String)session.getAttribute("valid");
	if(sessionid!=null)
	{
		response.sendRedirect("validateUser.htm");
	}
	%>
	
	<% 
		String errorMessage = (String)request.getAttribute("ErrorMessage");
	%>
	<%
		String invalid = (String) request.getAttribute("invalid");
		if (invalid != null) {
			out.println(invalid);
		}
		request.removeAttribute("invalid");
	%>
	
	<form id="loginForm" name="loginForm" method="POST"	action="validateUser.htm">
		<div align="center" style="width:100%;height:100%">
			<div id="errorContainer" class="error_message_header_container" style="padding-top:25px;height:80px;width:600px;">
				<%
					if(errorMessage != null)
					{
				%>
						<span class="error_message"><%=errorMessage %></span>
				<%
					}
				%>
			</div>
			<div style="position:relative;top:100px;width:671px;height:374px;background-image: url('image/nusyn_login_ui_bg.png?<%= NusynConstants.imageVersion %>')">
				<span id="input_username" class="input_field_1" style="position:absolute;top:87px;left:35px;" >Administrator</span>
				<input id="input_password" class="input_field_1" style="position:absolute;top:87px;right:35px;" type="password" id="password" name="password" placeholder="Password">
				<button id="Btn_login" type="button" class="Btn_login" onclick="generateNewAdminPassword()" style="position:absolute;left:296px;top:157px;"><span class="button_label_1">LOGIN</span></button>
				<span id="product_version" style="position:absolute;top:45px;left:325px;" class="product_version">Release <%= NusynConstants.releaseNumber %> (<%= NusynConstants.releaseNumber %>)</span>
			</div>
		</div>
	</form>
</body>
</html>