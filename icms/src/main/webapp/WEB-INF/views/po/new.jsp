<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<c:set var="widgetLabel" value="po.new"/>
<c:if test="${ !empty purchaseOrder.id }">
	<c:set var="widgetLabel" value="po.edit"/>
</c:if>

<commons:widget-header widgetLogo="barcode" widgetLabel="${ widgetLabel }" />

<commons:notification-message/>

<form:form class="form-horizontal" commandName="purchaseOrder" method="POST"
	action="${pageContext.request.contextPath}/po/save/submit">
	
	<form:hidden path="id"/>
	
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group <form:errors path="vendor.id" cssClass="error">error</form:errors>">
				<label class="control-label" for="vendor.id">
					<fmt:message key="vendor.label"/>
					<span class="mandatory"><fmt:message key="general.mandatory"/></span>
				</label>
				<div class="controls">
					<form:select path="vendor.id">
						<form:option value=""></form:option>
						<form:options items="${ allVendorList }" itemLabel="companyName" itemValue="id"/>
					</form:select>
					<form:errors path="vendor.id" cssClass="help-inline"/>
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="control-group <form:errors path="poNumber" cssClass="error">error</form:errors>">
				<label class="control-label" for="poNumber">
					<fmt:message key="po.number"/>
					<span class="mandatory"><fmt:message key="general.mandatory"/></span>
				</label>
				<div class="controls">
					<form:input path="poNumber" />
					<form:errors path="poNumber" cssClass="help-inline"/>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group <form:errors path="poDate" cssClass="error">error</form:errors>">
				<label class="control-label" for="poDate">
					<fmt:message key="po.date"/>
					<span class="mandatory"><fmt:message key="general.mandatory"/></span>
				</label>
				<div class="controls">
					<form:input path="poDate" class="datepicker"/>
					<form:errors path="poDate" cssClass="help-inline"/>
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="control-group ">
				<label class="control-label" for="price">
					<fmt:message key="po.price"/>
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
		<button type="submit" class="btn btn-primary"><fmt:message key="general.submit"/></button>
		<button type="button" class="btn" onclick="cancel()"><fmt:message key="general.cancel"/></button>
	</div>

</form:form>

<script type="text/javascript">
function cancel(){
	document.location.href = "${pageContext.request.contextPath}/po/";
}
</script>

<commons:widget-footer />
<commons:footer />