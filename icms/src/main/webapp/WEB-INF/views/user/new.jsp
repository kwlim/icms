<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />
<commons:widget-header widgetLogo="eye-open" widgetLabel="user.new"/>

<fmt:message key='campaign.module' var="pageModule"/>
<c:choose>
	<c:when test="${ campaignForm.edit }">
		<fmt:message key='campaign.edit' var="pageTitle"/>
	</c:when>
	<c:otherwise>
		<fmt:message key='campaign.new' var="pageTitle"/>		
	</c:otherwise>
</c:choose>

<commons:notification-message/>

<form:form class="form-horizontal" commandName="userDTO" method="POST"
	action="${pageContext.request.contextPath}/user/save/submit">
	
	<form:hidden path="id"/>
	<form:hidden path="edit"/>
	
	<div class="control-group <form:errors path="username" cssClass="error">error</form:errors>">
		<label class="control-label" for="username">
			<fmt:message key="general.username"/>
			<span class="mandatory"><fmt:message key="general.mandatory"/></span>
		</label>
		<div class="controls">
			<form:input path="username" />
			<form:errors path="username" cssClass="help-inline"/>
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
	
	<div class="control-group">
		<label class="control-label" for="dob">
			<fmt:message key="user.isAdmin"/>
		</label>
		<div class="controls">
			<form:checkbox path="isAdmin"/>
		</div>
	</div>
	
	<div class="control-group <form:errors path="groupId" cssClass="error">error</form:errors>">
		<label class="control-label" for="groupId">
			<fmt:message key="user.group"/>
			<span class="mandatory"><fmt:message key="general.mandatory"/></span>
		</label>
		<div class="controls">
			<form:select path="groupId">
				<form:option value=""></form:option>
				<form:options items="${ allGroupList }" itemLabel="name" itemValue="id"/>
			</form:select>
			<form:errors path="groupId" cssClass="help-inline"/>
		</div>
	</div>
	
	<div class="control-group <form:errors path="status" cssClass="error">error</form:errors>">
		<label class="control-label" for="status">
			<fmt:message key="user.status"/>
			<span class="mandatory"><fmt:message key="general.mandatory"/></span>
		</label>
		<div class="controls">
			<form:select path="status">
				<form:option value=""></form:option>
				<c:forEach var="tempStatus" items="${ userStatusList }">
					<fmt:message key="user.status.${ tempStatus.value }" var="statusLabel"/>
					<form:option value="${ tempStatus.value }"><c:out value="${ statusLabel }"/></form:option>
				</c:forEach>
			</form:select>
			<form:errors path="status" cssClass="help-inline"/>
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
		<button type="button" class="btn" onclick="cancel()"><fmt:message key="general.cancel"/></button>
	</div>

</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.9.1.custom.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
    
});

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/user/";
}

</script>

<commons:widget-footer />
<commons:footer />