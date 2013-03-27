<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<c:set var="widgetLabel" value="jo.new"/>
<c:if test="${ !empty jobOrder.id }">
	<c:set var="widgetLabel" value="jo.edit"/>
</c:if>

<commons:notification-message/>

<commons:widget-header widgetLogo="barcode" widgetLabel="${ widgetLabel }" />

<form:form class="form-horizontal" commandName="jobOrder" method="POST"
	action="${pageContext.request.contextPath}/jo/save/submit">
	
	<form:hidden path="id"/>
	
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group <form:errors path="customer.id" cssClass="error">error</form:errors>">
				<label class="control-label" for="customer.id">
					<fmt:message key="customer.label"/>
					<span class="mandatory"><fmt:message key="general.mandatory"/></span>
				</label>
				<div class="controls">
					<form:select path="customer.id">
						<form:option value=""></form:option>
						<form:options items="${ allCustomerList }" itemLabel="carPlateNumber" itemValue="id"/>
					</form:select>
					<form:errors path="customer.id" cssClass="help-inline"/>
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="control-group <form:errors path="jobNumber" cssClass="error">error</form:errors>">
				<label class="control-label" for="jobNumber">
					<fmt:message key="jo.number"/>
					<span class="mandatory"><fmt:message key="general.mandatory"/></span>
				</label>
				<div class="controls">
					<form:input path="jobNumber" />
					<form:errors path="jobNumber" cssClass="help-inline"/>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group <form:errors path="jobDate" cssClass="error">error</form:errors>">
				<label class="control-label" for="jobDate">
					<fmt:message key="jo.jobDate"/>
					<span class="mandatory"><fmt:message key="general.mandatory"/></span>
				</label>
				<div class="controls">
					<form:input path="jobDate" class="datepicker"/>
					<form:errors path="jobDate" cssClass="help-inline"/>
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="control-group ">
				<label class="control-label" for="price">
					<fmt:message key="jo.price"/>
				</label>
				<div class="controls">
					<form:input path="price" />
				</div>
			</div>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label" for="remark">
			<fmt:message key="general.remark"/>
		</label>
		<div class="controls">
			<form:textarea path="remark" class="input-xlarge" rows="3"/>
		</div>
	</div>
	<div class="form-actions">
		<button type="submit" class="btn btn-primary"><fmt:message key="general.save"/></button>
		<button type="button" class="btn" onclick="cancel()"><fmt:message key="general.cancel"/></button>
	</div>

</form:form>
<commons:widget-footer />

<script type="text/javascript">

$(document).ready(function() {
	
});

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/jo/";
}
</script>


<commons:footer />