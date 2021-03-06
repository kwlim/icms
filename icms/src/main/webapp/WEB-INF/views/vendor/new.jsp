<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<c:set var="widgetLabel" value="vendor.new"/>
<c:if test="${ !empty vendor.id }">
	<c:set var="widgetLabel" value="vendor.edit"/>
</c:if>

<commons:widget-header widgetLogo="barcode" widgetLabel="${ widgetLabel }" />

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
			<form:input path="companyName" cssClass="input-xxlarge"/>
			<form:errors path="companyName" cssClass="help-inline"/>
		</div>
	</div>
	<div class="control-group ">
		<label class="control-label" for="contactPerson">
			<fmt:message key="vendor.contact.person"/>
		</label>
		<div class="controls">
			<form:input path="contactPerson" cssClass="input-xlarge"/>
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
	<div class="control-group ">
		<label class="control-label" for="officeNumber">
			<fmt:message key="vendor.office.number"/>
		</label>
		<div class="controls">
			<form:input path="officeNumber" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="address">
			<fmt:message key="vendor.address"/>
		</label>
		<div class="controls">
			<form:textarea path="address" cssClass="input-xxlarge" rows="5"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="remark">
			<fmt:message key="general.remark"/>
		</label>
		<div class="controls">
			<form:textarea path="remark" cssClass="input-xxlarge" rows="5"/>
		</div>
	</div>
	<div class="form-actions">
		<button type="submit" class="btn btn-primary"><fmt:message key="general.submit"/></button>
		<button type="button" class="btn" onclick="cancel()"><fmt:message key="general.cancel"/></button>
	</div>

</form:form>

<script type="text/javascript">
function cancel(){
	document.location.href = "${pageContext.request.contextPath}/vendor/";
}
</script>

<commons:widget-footer />
<commons:footer />