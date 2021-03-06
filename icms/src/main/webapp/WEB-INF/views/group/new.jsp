<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />
<commons:widget-header widgetLogo="th" widgetLabel="user.group.new" />

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

<form:form class="form-horizontal" commandName="groupDTO" method="POST"
	action="${pageContext.request.contextPath}/group/save/submit">
	
	<form:hidden path="id"/>
	<form:hidden path="defaultAdminGroup"/>
	
	<div class="control-group <form:errors path="name" cssClass="error">error</form:errors>">
		<label class="control-label" for="name">
			<fmt:message key="general.name"/>
			<span class="mandatory"><fmt:message key="general.mandatory"/></span>
		</label>
		<div class="controls">
			<c:choose>
				<c:when test="${ groupForm.defaultAdminGroup }">
					<form:input path="name" disabled="true"/>
					<form:hidden path="name" />
				</c:when>
				<c:otherwise>
					<form:input path="name" />
				</c:otherwise>
			</c:choose>
			<form:errors path="name" cssClass="help-inline"/>
		</div>
	</div>
	
	<c:forEach var="roleEntry" items="${ roleMap }">
		<div style="margin-top: 30px">
		<fieldset>
			<legend><span><c:out value="${ roleEntry.key }"/></span></legend>
		</fieldset>
		
		<fieldset>
		<c:forEach var="permission" items="${ roleEntry.value }">
			<label class="checkbox inline" style="padding-left: 50px">
				<c:set var="checkboxSelect" value=""/>
				<c:if test="${ existingRoleMap[permission] }">
					<c:set var="checkboxSelect" value="checked"/>
				</c:if>
				<fmt:message var="permissionLabel" key="group.permission.${permission}"/>
				<input type="checkbox" name="permission" value="${ permission }" <c:out value='${ checkboxSelect }'/> /><c:out value="${ permissionLabel }"/>
			</label>
		</c:forEach>
		</fieldset>
		</div>
	</c:forEach>
	
	<div class="form-actions">
		<button type="submit" class="btn btn-primary"><fmt:message key="general.submit"/></button>
		<button type="button" class="btn" onclick="cancel()"><fmt:message key="general.cancel"/></button>
	</div>

</form:form>


<script type="text/javascript">

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/group/";
}

</script>

<commons:widget-footer />
<commons:footer />