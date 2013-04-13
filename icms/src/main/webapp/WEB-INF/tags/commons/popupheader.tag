<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<%@ attribute name="title" required="false" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<!-- start: Meta -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="Mr Lim & Mrs Lim">
	<title><fmt:message key="general.app.name"/></title>
	<title><fmt:message key="general.app.name"/></title>
	<!-- end: Meta -->
	
	<!-- start: Mobile Specific -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- end: Mobile Specific -->
	
	<!-- start: CSS -->
	<jsp:include page="/WEB-INF/views/includes/css.jsp" />
	<jsp:include page="/WEB-INF/views/includes/scripts.jsp" />
	<!-- end: CSS -->
</head>

<body>
	
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<h2><span><c:out value="${ title }"/></span></h2>
				
			</div>
		</div>
	</div>
	
	<div class="container-fluid">
	<div class="row-fluid">
	
		
		
		
		