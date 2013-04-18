<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<!-- start: Meta -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="Mr Lim & Mrs Lim">
	<title><fmt:message key="general.app.name"/></title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico"/>
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
	<!-- start: Header -->
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="${pageContext.request.contextPath}/dashboard/"><span><fmt:message key="general.app.name"/></span></a>
								
				<!-- start: Header Menu -->
				<div class="nav-no-collapse header-nav">
					<ul class="nav pull-right">
						<!-- end: Message Dropdown -->
						<li class="dropdown">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="halflings-icon white wrench"></i>
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li><a href="${pageContext.request.contextPath}/billing/" title=""><i class="halflings-icon white road"></i> <fmt:message key="billing.label"/></a></li>
								<li><a href="${pageContext.request.contextPath}/group/"><i class="halflings-icon white th"></i> <fmt:message key="user.group"/></a></li>
								<li><a href="${pageContext.request.contextPath}/user/"><i class="halflings-icon white eye-open"></i> <fmt:message key="user.label"/></a></li>
							</ul>
						</li>
						<!-- start: User Dropdown -->
						<sec:authorize access="isAuthenticated()">
						<li class="dropdown">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="halflings-icon white user"></i> <c:out value="${ pageContext['request'].userPrincipal.principal.fullname }"/>
								<span class="caret"></span>
							</a>
							
							<ul class="dropdown-menu">
								<li><a href="${ pageContext.request.contextPath }/profile"><i class="halflings-icon white user"></i> Profile</a></li>
								<li><a href="${ pageContext.request.contextPath }/company"><i class="halflings-icon white user"></i> Company</a></li>
								<li><a href="${ pageContext.request.contextPath }/logout"><i class="halflings-icon white off"></i> Logout</a></li>
							</ul>
						</li>
						</sec:authorize>
						<!-- end: User Dropdown -->
					</ul>
				</div>
				<!-- end: Header Menu -->
				
			</div>
		</div>
	</div>
	<!-- start: Header -->
	
	<div class="container-fluid">
	<div class="row-fluid">
	
	<commons:left />
		
		
		
		