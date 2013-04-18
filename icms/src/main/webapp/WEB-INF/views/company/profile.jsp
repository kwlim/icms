<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:notification-message/>

<commons:widget-header widgetLogo="eye-open" widgetLabel="company.label"/>

<form:form class="form-horizontal" commandName="company" method="POST"
	action="${pageContext.request.contextPath}/company">
	
	<form:hidden path="id"/>
	<form:hidden path="companyKey"/>
	
	<div class="control-group <form:errors path="name" cssClass="error">error</form:errors>">
		<label class="control-label" for="name">
			<fmt:message key="company.name"/>
		</label>
		<div class="controls">
			<form:input path="name"/>
			<form:errors path="name" cssClass="help-inline"/>
		</div>
	</div>
	
	<div class="control-group <form:errors path="registrationNumber" cssClass="error">error</form:errors>">
		<label class="control-label" for=registrationNumber>
			<fmt:message key="user.firstname"/>
			<span class="mandatory"><fmt:message key="company.registrationNumber"/></span>
		</label>
		<div class="controls">
			<form:input path="registrationNumber" />
			<form:errors path="registrationNumber" cssClass="help-inline"/>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label" for="address">
			<fmt:message key="company.address"/>
		</label>
		<div class="controls">
			<form:textarea path="address" class="input-xlarge" rows="5"/>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label" for="phoneNumber">
			<fmt:message key="company.phoneNumber"/>
		</label>
		<div class="controls">
			<form:input path="phoneNumber" />
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label" for="faxNumber">
			<fmt:message key="company.faxNumber"/>
		</label>
		<div class="controls">
			<form:input path="faxNumber" />
		</div>
	</div>
	
	<div class="form-actions">
		<button type="submit" class="btn btn-primary"><fmt:message key="general.submit"/></button>
	</div>
	
</form:form>

<script type="text/javascript">

</script>

<commons:widget-footer />
<commons:footer />