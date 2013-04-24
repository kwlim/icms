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
						<sec:authorize access="hasAnyRole('ROLE_BILLING_VIEW', 'ROLE_BILLING_ADD_EDIT', 'ROLE_BILLING_DELETE',
							'ROLE_GROUP_VIEW', 'ROLE_GROUP_ADD_EDIT', 'ROLE_GROUP_DELETE',
							'ROLE_USER_VIEW', 'ROLE_USER_ADD_EDIT', 'ROLE_USER_DELETE')">
						<li class="dropdown">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="halflings-icon white wrench"></i>
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<sec:authorize access="hasAnyRole('ROLE_BILLING_VIEW', 'ROLE_BILLING_ADD_EDIT', 'ROLE_BILLING_DELETE')">
									<li><a href="${pageContext.request.contextPath}/billing/" title=""><i class="halflings-icon white road"></i> <fmt:message key="billing.label"/></a></li>
								</sec:authorize>
								<sec:authorize access="hasAnyRole('ROLE_USER_VIEW', 'ROLE_USER_ADD_EDIT', 'ROLE_USER_DELETE')">
									<li><a href="${pageContext.request.contextPath}/user/"><i class="halflings-icon white eye-open"></i> <fmt:message key="user.label"/></a></li>
								</sec:authorize>
								<sec:authorize access="hasAnyRole('ROLE_GROUP_VIEW', 'ROLE_GROUP_ADD_EDIT', 'ROLE_GROUP_DELETE')">
									<li><a href="${pageContext.request.contextPath}/group/"><i class="halflings-icon white th"></i> <fmt:message key="user.group"/></a></li>
								</sec:authorize>
							</ul>
						</li>
						</sec:authorize>
						<!-- start: User Dropdown -->
						<sec:authorize access="isAuthenticated()">
						<li class="dropdown">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="halflings-icon white user"></i> <c:out value="${ pageContext['request'].userPrincipal.principal.fullname }"/>
								<span class="caret"></span>
							</a>
							
							<ul class="dropdown-menu">
								<li><a href="${ pageContext.request.contextPath }/profile"><i class="halflings-icon white user"></i> <fmt:message key="user.profile"/></a></li>
								<sec:authorize access="hasAnyRole('ROLE_COMPANY_VIEW', 'ROLE_COMPANY_ADD_EDIT', 'ROLE_COMPANY_DELETE')">
									<li><a href="${ pageContext.request.contextPath }/company"><i class="halflings-icon white user"></i> <fmt:message key="company.label"/></a></li>
								</sec:authorize>
								<li><a href="${ pageContext.request.contextPath }/logout"><i class="halflings-icon white off"></i> <fmt:message key="general.logout"/></a></li>
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
		
		
		
		