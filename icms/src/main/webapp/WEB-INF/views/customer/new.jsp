<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<c:set var="widgetLabel" value="customer.new"/>
<c:if test="${ !empty customer.id }">
	<c:set var="widgetLabel" value="customer.edit"/>
</c:if>

<commons:widget-header widgetLogo="globe" widgetLabel="${ widgetLabel }" />

<commons:notification-message/>

<form:form class="form-horizontal" commandName="customer" method="POST"
	action="${pageContext.request.contextPath}/customer/save/submit">
	
	<form:hidden path="id"/>
	
	<div class="control-group <form:errors path="carPlateNumber" cssClass="error">error</form:errors>">
		<label class="control-label" for="carPlateNumber">
			<fmt:message key="customer.carPlateNumber"/>
			<span class="mandatory"><fmt:message key="general.mandatory"/></span>
		</label>
		<div class="controls">
			<form:input path="carPlateNumber" />
			<form:errors path="carPlateNumber" cssClass="help-inline"/>
		</div>
	</div>
	<div class="control-group ">
		<label class="control-label" for="name">
			<fmt:message key="general.name"/>
		</label>
		<div class="controls">
			<form:input path="name" />
		</div>
	</div>
	<div class="control-group ">
		<label class="control-label" for="contactNumber">
			<fmt:message key="customer.contact.number"/>
		</label>
		<div class="controls">
			<form:input path="contactNumber" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="address">
			<fmt:message key="customer.address"/>
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
	document.location.href = "${pageContext.request.contextPath}/customer/";
}

</script>

<commons:widget-footer />
<commons:footer />