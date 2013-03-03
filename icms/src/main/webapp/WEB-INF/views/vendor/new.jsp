<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />
<commons:widget-header widgetLogo="barcode" widgetLabel="item.new" />

<c:choose>
	<c:when test="${ campaignForm.edit }">
		<fmt:message key='campaign.edit' var="pageTitle"/>
	</c:when>
	<c:otherwise>
		<fmt:message key='campaign.new' var="pageTitle"/>		
	</c:otherwise>
</c:choose>

<commons:notification-message/>

<form:form class="form-horizontal" commandName="vendor" method="POST"
	action="${pageContext.request.contextPath}/vendor/save/submit">
	
	<form:hidden path="id"/>
	
	<div class="control-group <form:errors path="companyName" cssClass="error">error</form:errors>">
		<label class="control-label" for="companyName">
			<fmt:message key="vendor.company.name"/>
			<span class="mandatory"><fmt:message key="general.mandatory"/></span>
		</label>
		<div class="controls">
			<form:input path="companyName" />
			<form:errors path="companyName" cssClass="help-inline"/>
		</div>
	</div>
	<div class="control-group ">
		<label class="control-label" for="contactPerson">
			<fmt:message key="vendor.contact.person"/>
		</label>
		<div class="controls">
			<form:input path="contactPerson" />
		</div>
	</div>
	<div class="control-group ">
		<label class="control-label" for="contactNumber">
			<fmt:message key="vendor.contact.number"/>
		</label>
		<div class="controls">
			<form:input path="contactNumber" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="address">
			<fmt:message key="vendor.address"/>
		</label>
		<div class="controls">
			<form:textarea path="address" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="remark">
			<fmt:message key="general.remark"/>
		</label>
		<div class="controls">
			<form:textarea path="remark" />
		</div>
	</div>
	<div class="form-actions">
		<button type="submit" class="btn btn-primary"><fmt:message key="general.submit"/></button>
		<button type="button" class="btn" onclick="cancel()"><fmt:message key="general.cancel"/></button>
	</div>

</form:form>

<script type="text/javascript">

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/vendor/list";
}

</script>

<commons:widget-footer />
<commons:footer />