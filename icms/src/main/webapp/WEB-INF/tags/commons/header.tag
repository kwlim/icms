<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>SPRING MVC PROJECT</title>
<jsp:include page="/WEB-INF/views/includes/css.jsp" />
</head>

<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="${ pageContext.request.contextPath }/home">Project Name</a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						
						<sec:authorize access="!isAuthenticated()">
							<li><a href="${ pageContext.request.contextPath }/login">Login</a></li>
						</sec:authorize>
						
						<sec:authorize access="isAuthenticated()">
							<li><a href="${ pageContext.request.contextPath }/user/list">User Listing</a></li>
							<li><a href="${ pageContext.request.contextPath }/group/list">Group Listing</a></li>
							<li><a href="${ pageContext.request.contextPath }/j_spring_security_logout">Logout</a></li>
							<li><a href="${ pageContext.request.contextPath }/profile">Profile</a></li>
						</sec:authorize>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<div class="container">