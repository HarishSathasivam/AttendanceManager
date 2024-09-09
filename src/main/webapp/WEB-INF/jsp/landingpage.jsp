<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.nusyn.license.util.NusynConstants"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="image/nusyn_favicon.png?<%= NusynConstants.imageVersion %>"/>

<link rel="stylesheet" type="text/css" href="css/master.css?<%= NusynConstants.cssVersion %>">
<link rel="stylesheet" type="text/css" href="css/nusyn_main.css?1<%= NusynConstants.cssVersion %>">
<link rel="stylesheet" type="text/css" href="css/login.css?<%= NusynConstants.cssVersion %>">
<link rel="stylesheet" type="text/css" href="css/jquery-ui.theme.css?<%= NusynConstants.cssVersion %>">

<script type="text/javascript" src="js/jquery-1.11.1.min.js?<%= NusynConstants.javaScriptVersion %>"></script>
<script type="text/javascript" src="js/jquery-ui.js?<%= NusynConstants.javaScriptVersion %>"></script>
<style type="text/css">
    /* make default button size sane */
    .ui-button-text {font-size: 12px;}
    .ui-widget{font-size:12px;}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		//Position layout items
		$( "button" ).button();
	});
	function logout()
	{
		
		var formToSubmit = $("#mainForm");
		formToSubmit.attr("action", "logoutpage.htm");
		console.log("logout = submit")
		formToSubmit.submit();
	}
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
</script>

</head>
<body style=" top:100px; position :absolute;width:100%;height:100%;background-image: url('image/nusyn_bg_tile.png?<%= NusynConstants.imageVersion %>' )">
	<div id="Top_Menu_Container" class="Top_Menu_Container" style="position:relative ; top:-100px; width:100%; height:100px;background-image: url('image/metro_topcontainer_bg_tile.png?<%= NusynConstants.imageVersion %>');">
		<img id="Top_Menu_Logo" class="Top_Menu_Logo" onclick="goToLicenseStatus()" style="cursor:pointer;max-height: 32px ;left:10px; position:relative; top:10px;"  src="image/metro_logo_topmenu.png?<%= NusynConstants.imageVersion %>"/>
		<span id="Top_Menu_Product_Name" class="Top_Menu_Product_Name" style ="position:relative;left:35px; color: #bac1c4;">NusynLicenseManager / Landing<br></span>
		<button id="lagout" onclick="logout()" style='position:relative;top:0px;z-index:5; width:100px;height:30px;left:85%;'>Logout</button>
		<form id="mainForm" name="mainForm" method="POST" action="">
		</form>
	</div>

</body>
</html>