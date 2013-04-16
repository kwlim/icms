<%@ include file="/WEB-INF/views/includes/taglibs.jsp" %>

	<!-- start: Main Menu -->
	<div id="sidebar-left" class="span1">
		<div class="nav-collapse sidebar-nav">
			<ul class="nav nav-tabs nav-stacked main-menu">
				<li><a href="${pageContext.request.contextPath}/dashboard/"><i class="fa-icon-dashboard"></i><span class="hidden-tablet"> Dashboard</span></a></li>	
				<li><a href="${pageContext.request.contextPath}/customer/"><i class="fa-icon-globe"></i><span class="hidden-tablet"> <fmt:message key="customer.label"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/itemCategory/"><i class="fa-icon-tags"></i><span class="hidden-tablet"> <fmt:message key="item.category.label"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/item/"><i class="fa-icon-shopping-cart"></i><span class="hidden-tablet"> <fmt:message key="item.label"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/vendor/"><i class="fa-icon-barcode"></i><span class="hidden-tablet"> <fmt:message key="vendor.label"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/po/"><i class="fa-icon-credit-card"></i><span class="hidden-tablet"> <fmt:message key="po.label"/></span></a></li>
				<li><a href="${pageContext.request.contextPath}/job/"><i class="fa-icon-tasks"></i><span class="hidden-tablet"> <fmt:message key="job.label"/></span></a></li>
				<li><a href="#" class="dropmenu"><i class="fa-icon-tasks"></i><span class="hidden-tablet"> Report</span></a>
					<ul style="dipslay: block;">
						<li><a href="${pageContext.request.contextPath}/report/priceByCategory"><i class="fa-icon-tasks"></i><span class="hidden-tablet">Item Category Report</span></a></li>
					</ul>
				</li>
				<%-- <li><a href="${pageContext.request.contextPath}/billing/list"><i class="fa-icon-money"></i><span class="hidden-tablet"> <fmt:message key="billing.label"/></span></a></li> --%>
				<%-- <li><a href="${pageContext.request.contextPath}/user/list"><i class="fa-icon-user"></i><span class="hidden-tablet"> <fmt:message key="user.label"/></span></a></li> --%>
				<%-- <li><a href="${pageContext.request.contextPath}/group/list"><i class="fa-icon-group"></i><span class="hidden-tablet"> <fmt:message key="user.group"/></span></a></li> --%>
				<%-- <li><a href="${pageContext.request.contextPath}/vendor/list"><i class="fa-icon-cargo"></i><span class="hidden-tablet"> <fmt:message key="vendor.label"/></span></a></li> --%>
			</ul>
		</div>
	</div>
	<!-- end: Main Menu -->
	
	<!-- start: Content -->
	<div id="content" class="span11">