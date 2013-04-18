<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:notification-message/>

<commons:widget-header widgetLogo="eye-open" widgetLabel="user.profile"/>

<form:form class="form-horizontal" commandName="userProfileDTO" method="POST"
	action="${pageContext.request.contextPath}/profile">
	
	<form:hidden path="id"/>
	<form:hidden path="username"/>
	
	<div class="control-group <form:errors path="username" cssClass="error">error</form:errors>">
		<label class="control-label" for="username">
			<fmt:message key="general.username"/>
		</label>
		<div class="controls">
			<c:out value="${ userProfileDTO.username }"/>
		</div>
	</div>
	
	<div class="control-group <form:errors path="firstname" cssClass="error">error</form:errors>">
		<label class="control-label" for=firstname>
			<fmt:message key="user.firstname"/>
			<span class="mandatory"><fmt:message key="general.mandatory"/></span>
		</label>
		<div class="controls">
			<form:input path="firstname" />
			<form:errors path="firstname" cssClass="help-inline"/>
		</div>
	</div>
	
	<div class="control-group <form:errors path="lastname" cssClass="error">error</form:errors>">
		<label class="control-label" for=lastname>
			<fmt:message key="user.lastname"/>
			<span class="mandatory"><fmt:message key="general.mandatory"/></span>
		</label>
		<div class="controls">
			<form:input path="lastname" />
			<form:errors path="lastname" cssClass="help-inline"/>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label" for="nickname">
			<fmt:message key="user.nickname"/>
		</label>
		<div class="controls">
			<form:input path="nickname" />
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label" for="address">
			<fmt:message key="user.address"/>
		</label>
		<div class="controls">
			<form:textarea path="address" class="input-xlarge" rows="5"/>
		</div>
	</div>
	
	<div class="control-group <form:errors path="email" cssClass="error">error</form:errors>">
		<label class="control-label" for="email">
			<fmt:message key="user.email"/>
			<span class="mandatory"><fmt:message key="general.mandatory"/></span>
		</label>
		<div class="controls">
			<form:input path="email" />
			<form:errors path="email" cssClass="help-inline"/>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label" for="dob">
			<fmt:message key="user.dob"/>
		</label>
		<div class="controls">
			<form:input path="dob" class="datepicker"/>
		</div>
	</div>
	
	<div class="control-group <form:errors path="password" cssClass="error">error</form:errors>">
		<label class="control-label" for="password">
			<fmt:message key="user.password"/>
		</label>
		<div class="controls">
			<form:password path="password" />
			<form:errors path="password" cssClass="help-inline"/>
		</div>
	</div>
	
	<div class="control-group <form:errors path="retypePassword" cssClass="error">error</form:errors>">
		<label class="control-label" for="retypePassword">
			<fmt:message key="user.retypePassword"/>
		</label>
		<div class="controls">
			<form:password path="retypePassword" />
			<form:errors path="retypePassword" cssClass="help-inline"/>
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