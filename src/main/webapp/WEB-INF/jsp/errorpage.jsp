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
<% 
	String errorMessage = (String)request.getAttribute("ErrorMessage");
%>
<style type="text/css">
    /* make default button size sane */
    .ui-button-text {font-size: 12px;}
    .ui-widget{font-size:12px;}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		//Position layout items
		$( "#error_message_container" ).position({
	        of: $( "#back_button" ),
	        my: "left top",
	        at: "left bottom+20px",
	        collision: "none none"
	      });
		$( "#errorMessage" ).position({
	        of: $( "#error_message_container" ),
	        my: "center top",
	        at: "center top+20px",
	        collision: "none none"
	      });
		$( "button" ).button();
		$("#errorMessage").html('<%= errorMessage %>')
	});
	
	function goBack()
	{
		var formToSubmit = $("#mainForm");
		formToSubmit.attr("action", "logoutpage.htm");
		formToSubmit.submit();
	}
</script>

</head>
<body style="width:100%;height:100%;background-image: url('image/nusyn_bg_tile.png?<%= NusynConstants.imageVersion %>')">
	<button id="back_button" name="button" value="OK" type="button" onclick="goBack()" style="position:absolute;left:20px;top:20px;padding:5px;">BACK</button>
		
	<div id="error_message_container" class="error_message_container" style="height:100%;width:100%;">
		<span id="errorMessage" class="error_message" style="font-size: 20px;"></span>
	</div>
	<form id="mainForm" name="mainForm" method="POST" action="">
	</form>
</body>
</html>