<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />
<commons:widget-header widgetLogo="tags" widgetLabel="item.category.new"/>

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

<form:form class="form-horizontal" commandName="itemCategory" method="POST"
	action="${pageContext.request.contextPath}/item/category/save/submit">
	
	<form:hidden path="id"/>
	
	<div class="control-group <form:errors path="name" cssClass="error">error</form:errors>">
		<label class="control-label" for="name">
			<fmt:message key="general.name"/>
			<span class="mandatory"><fmt:message key="general.mandatory"/></span>
		</label>
		<div class="controls">
			<form:input path="name" />
			<form:errors path="name" cssClass="help-inline"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="remark">
			<fmt:message key="general.remark"/>
		</label>
		<div class="controls">
			<form:textarea path="remark"/>
		</div>
	</div>
	<div class="form-actions">
		<button type="submit" class="btn btn-primary"><fmt:message key="general.submit"/></button>
		<button type="button" class="btn" onclick="cancel()"><fmt:message key="general.cancel"/></button>
	</div>

</form:form>

<script type="text/javascript">

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/item/category/list";
}

</script>

<commons:widget-footer />
<commons:footer />