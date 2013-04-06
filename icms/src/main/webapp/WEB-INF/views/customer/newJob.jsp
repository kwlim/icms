<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<a href="${pageContext.request.contextPath}/job/new?customerId=${customer.id}" data-rel="tooltip" data-original-title="<fmt:message key='general.new'/>">
<i class="halflings-icon edit"></i>
</a>