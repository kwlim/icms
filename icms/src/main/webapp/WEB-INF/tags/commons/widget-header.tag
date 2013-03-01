<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<%@ attribute name="widgetLogo" required="false" %>
<%@ attribute name="widgetLabel" required="true" %>

<c:if test="${widgetLogo == null }">
	<c:set var="widgetLogo" value="align-justify"/>
</c:if>
	<div class="row-fluid sortable">		
		<div class="box span12">
			<div class="box-header" data-original-title>
				<h2>
					<i class="halflings-icon <c:out value="${widgetLogo}"/>"></i>
					<span class="break"></span>
					<fmt:message key="${widgetLabel}"/>
				</h2>
			</div>
			<div class="box-content">