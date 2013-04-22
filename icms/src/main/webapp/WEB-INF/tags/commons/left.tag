<%@ include file="/WEB-INF/views/includes/taglibs.jsp" %>

	<!-- start: Main Menu -->
	<div id="sidebar-left" class="span1">
		<div class="nav-collapse sidebar-nav">
			<ul class="nav nav-tabs nav-stacked main-menu">
				<li><a href="${pageContext.request.contextPath}/dashboard/"><i class="fa-icon-dashboard"></i><span class="hidden-tablet"> <fmt:message key="dashboard.label"/></span></a></li>	
				<sec:authorize access="hasAnyRole('ROLE_CUSTOMER_VIEW', 'ROLE_CUSTOMER_ADD_EDIT', 'ROLE_CUSTOMER_DELETE')">
					<li><a href="${pageContext.request.contextPath}/customer/"><i class="fa-icon-globe"></i><span class="hidden-tablet"> <fmt:message key="customer.label"/></span></a></li>
				</sec:authorize>
				<sec:authorize access="hasAnyRole('ROLE_ITEM_CATEGORY_VIEW', 'ROLE_ITEM_CATEGORY_ADD_EDIT', 'ROLE_ITEM_CATEGORY_DELETE')">
					<li><a href="${pageContext.request.contextPath}/itemCategory/"><i class="fa-icon-tags"></i><span class="hidden-tablet"> <fmt:message key="item.category.label"/></span></a></li>
				</sec:authorize>
				<sec:authorize access="hasAnyRole('ROLE_ITEM_VIEW', 'ROLE_ITEM_ADD_EDIT', 'ROLE_ITEM_DELETE')">
					<li><a href="${pageContext.request.contextPath}/item/"><i class="fa-icon-shopping-cart"></i><span class="hidden-tablet"> <fmt:message key="item.label"/></span></a></li>
				</sec:authorize>
				<sec:authorize access="hasAnyRole('ROLE_VENDOR_VIEW', 'ROLE_VENDOR_ADD_EDIT', 'ROLE_VENDOR_DELETE')">
					<li><a href="${pageContext.request.contextPath}/vendor/"><i class="fa-icon-barcode"></i><span class="hidden-tablet"> <fmt:message key="vendor.label"/></span></a></li>
				</sec:authorize>
				<sec:authorize access="hasAnyRole('ROLE_PURCHASE_VIEW', 'ROLE_PURCHASE_ADD_EDIT', 'ROLE_PURCHASE_DELETE')">
					<li><a href="${pageContext.request.contextPath}/po/"><i class="fa-icon-credit-card"></i><span class="hidden-tablet"> <fmt:message key="po.label"/></span></a></li>
				</sec:authorize>
				<sec:authorize access="hasAnyRole('ROLE_JOB_VIEW', 'ROLE_JOB_ADD_EDIT', 'ROLE_JOB_DELETE')">
					<li><a href="${pageContext.request.contextPath}/job/"><i class="fa-icon-tasks"></i><span class="hidden-tablet"> <fmt:message key="job.label"/></span></a></li>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_REPORT_VIEW')">
					<li><a href="#" class="dropmenu"><i class="fa-icon-tasks"></i><span class="hidden-tablet"> <fmt:message key="report.label"/></span></a>
						<ul style="dipslay: block;">
							<li><a href="${pageContext.request.contextPath}/report/priceByCategory"><i class="fa-icon-tasks"></i><span class="hidden-tablet"><fmt:message key="report.item.category"/></span></a></li>
						</ul>
					</li>
				</sec:authorize>
			</ul>
		</div>
	</div>
	<!-- end: Main Menu -->
	
	<!-- start: Content -->
	<div id="content" class="span11">