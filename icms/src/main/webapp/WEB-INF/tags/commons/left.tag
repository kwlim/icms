<%@ include file="/WEB-INF/views/includes/taglibs.jsp" %>

	<!-- start: Main Menu -->
	<div id="sidebar-left" class="span1">
		<div class="nav-collapse sidebar-nav">
			<ul class="nav nav-tabs nav-stacked main-menu">
				<li><a href="${pageContext.request.contextPath}/"><i class="fa-icon-bar-chart"></i><span class="hidden-tablet"> Dashboard</span></a></li>	
				<li><a href="${pageContext.request.contextPath}/item/category/list"><i class="fa-icon-tags"></i><span class="hidden-tablet"> <fmt:message key="item.category.label"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/item/list"><i class="fa-icon-barcode"></i><span class="hidden-tablet"> <fmt:message key="item.label"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/po/list"><i class="fa-icon-credit-card"></i><span class="hidden-tablet"> <fmt:message key="po.label"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/jo/list"><i class="fa-icon-tasks"></i><span class="hidden-tablet"> <fmt:message key="jo.label"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/billing/list"><i class="fa-icon-money"></i><span class="hidden-tablet"> <fmt:message key="billing.label"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/user/list"><i class="fa-icon-user"></i><span class="hidden-tablet"> <fmt:message key="user.label"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/customer/list"><i class="fa-icon-user-md"></i><span class="hidden-tablet"> <fmt:message key="customer.label"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/vendor/list"><i class="fa-icon-group"></i><span class="hidden-tablet"> <fmt:message key="vendor.label"/></span></a></li>
				
			</ul>
		</div>
	</div>
	<!-- end: Main Menu -->
	
	<!-- start: Content -->
	<div id="content" class="span11">