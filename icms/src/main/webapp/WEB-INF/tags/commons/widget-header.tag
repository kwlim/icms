<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<%@ attribute name="widgetLogo" required="false" %>
<%@ attribute name="widgetLabel" required="true" %>
<%@ attribute name="includeAction" required="false" %>

<c:if test="${widgetLogo == null }">
	<c:set var="widgetLogo" value="align-justify"/>
</c:if>
	<div class="row-fluid ">		
		<div class="box span12">
			<div class="box-header" data-original-title>
				<h2>
					<i class="halflings-icon <c:out value="${widgetLogo}"/>"></i>
					<span class="break"></span>
					<fmt:message key="${widgetLabel}"/>
				</h2>
				<c:if test="${ ! empty includeAction }">
					<div class="box-icon">
						<jsp:include page="${ includeAction }" flush="true"/>
					</div>
				</c:if>
			</div>
			<div class="box-content">