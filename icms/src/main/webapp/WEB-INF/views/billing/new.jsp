<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:notification-message/>

<commons:widget-header widgetLogo="barcode" widgetLabel="billing.label" />

<form:form class="form-horizontal" commandName="billing" method="POST"
	action="${pageContext.request.contextPath}/billing/save/submit">
	
	<form:hidden path="id"/>
	<form:hidden path="billingKey"/>
	
	<div class="control-group <form:errors path="expiryDate" cssClass="error">error</form:errors>">
		<label class="control-label" for="expiryDate"> 
			<fmt:message key="billing.expiryDate" /> 
			<span class="mandatory"><fmt:message key="general.mandatory" /></span>
		</label>
		<div class="controls">
			<form:input path="expiryDate" cssClass="datepicker input-medium" />
			<div>
				<form:errors path="expiryDate" cssClass="help-inline" />
			</div>
		</div>
	</div>

	<div class="form-actions">
		<button type="submit" class="btn btn-primary"><fmt:message key="general.submit"/></button>
		<button type="button" class="btn" onclick="cancel()"><fmt:message key="general.cancel"/></button>
	</div>

</form:form>
<commons:widget-footer />

<commons:widget-header widgetLogo="barcode" widgetLabel="payment.list" />

<form:form class="form-horizontal" commandName="payment" method="POST"
	action="${pageContext.request.contextPath}/billing/payment/save/submit">
	
	<form:hidden path="id"/>
	<form:hidden path="billing.id"/>
	
	<div class="row-fluid">
		<div class="span5">
			<div class="control-group <form:errors path="paymentDate" cssClass="error">error</form:errors>">
				<label class="control-label" for="paymentDate">
					<fmt:message key="payment.paymentDate"/>
					<span class="mandatory"><fmt:message key="general.mandatory"/></span>
				</label>
				<div class="controls">
					<form:input path="paymentDate" cssClass="datepicker input-medium"/>
					<div><form:errors path="paymentDate" cssClass="help-inline"/></div>
				</div>
			</div>
		</div>
		<div class="span5">
			<div class="control-group <form:errors path="amount" cssClass="error">error</form:errors>">
				<label class="control-label" for="amount">
					<fmt:message key="payment.amount"/>
				</label>
				<div class="controls">
					<form:input path="amount" cssClass="input-medium"/>
					<div><form:errors path="amount" cssClass="help-inline"/></div>
				</div>
			</div>
		</div>
		<div class="span2">
			<button class="btn btn-primary">Add Payment</button>
		</div>
	</div>
	

</form:form>

<form id="filterForm" method="GET" action="?" class="form-search">
	<div class="control-group">
		<button type="submit" class="btn btn-danger" onclick="return deleteRecords()">
			<i class="halflings-icon trash"></i> <fmt:message key="general.delete"/>
		</button>
	</div>
	<display:table id="${id}" name="${rows}" size="${size}" pagesize="10"
		export="false" class="table table-striped table-bordered table-condensed"
		requestURI="?" sort="external" partialList="true">
		<display:column media="html" title="<input type='checkbox' id='selectall'/>" class="selectAll">
			<input id="id" name="id" type="checkbox" value="${paymentList.id}" />
		</display:column>
		<display:column titleKey="payment.paymentDate" sortable="true">
			<c:if test="${ paymentList.paymentDate != null }">
				<fmt:formatDate value="${ paymentList.paymentDate }" pattern="dd MMM yyyy" />
			</c:if>
		</display:column>
		<display:column titleKey="payment.amount" property="amount" sortable="true"/>
		<display:column titleKey="general.lastUpdatedDate" sortable="true">
			<c:if test="${ paymentList.modifiedDate != null }">
				<fmt:formatDate value="${ paymentList.modifiedDate }" pattern="dd MMM yyyy hh:mm a" />
			</c:if>
		</display:column>
		<display:column media="html"  titleKey="general.actions" sortable="false" class="tableAction">
			<c:url var="editUrl" value="/billing/payment/edit/${ paymentList.id }"/>
			<a href="${ editUrl }" class="btn btn-info" data-rel="tooltip" data-original-title="<fmt:message key='general.edit'/>">
				<i class="halflings-icon edit" ></i>                                       
			</a>
		</display:column>
	</display:table>
</form>

<script type="text/javascript">

function deleteRecords() {
    if ($("[name=id]:checked").length > 0) {
        if (confirm('<fmt:message key="general.delete.confirmation"/>')) {
            $("#filterForm").attr("method", "POST");
            $("#filterForm").attr("action", "${pageContext.request.contextPath}/billing/payment/delete");
            return true;
        }
    }
    return false;
}
</script>
<commons:widget-footer />
<commons:footer />