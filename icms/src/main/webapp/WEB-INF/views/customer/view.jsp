<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:notification-message/>

<commons:widget-header widgetLogo="globe" widgetLabel="customer.view.details" />

<div class="row-fluid">
	<div class="span4">
		<div class="control-group">
			<label class="control-label">
				<h3>
					<fmt:message key="customer.carPlateNumber" />
				</h3>
			</label>
		</div>
		<div class="controls">
			<c:out value="${ customer.carPlateNumber }" />
		</div>
	</div>
	<div class="span4">
		<div class="control-group">
			<label class="control-label">
				<h3>
					<fmt:message key="general.name" />
				</h3>
			</label>
			<div class="controls">
				<c:out value="${ customer.name }" />
			</div>
		</div>
	</div>
	<div class="span4">
		<div class="control-group ">
			<label class="control-label">
				<h3>
					<fmt:message key="customer.contact.number" />
				</h3>
			</label>
			<div class="controls">
				<c:out value="${ customer.contactNumber }" />
			</div>
		</div>
	</div>
</div>

<div class="row-fluid">
	<div class="span6">
		<div class="control-group ">
			<label class="control-label">
				<h3>
					<fmt:message key="customer.address" />
				</h3>
			</label>
			<div class="controls">
				<c:out value="${ customer.address }" />
			</div>
		</div>
	</div>
	<div class="span6">
		<div class="control-group ">
			<label class="control-label">
				<h3>
					<fmt:message key="general.remark" />
				</h3>
			</label>
			<div class="controls">
				<c:out value="${ customer.remark }" />
			</div>
		</div>
	</div>
</div>

<commons:widget-footer />

<commons:widget-header widgetLogo="globe" widgetLabel="customer.view.history" />

<form id="filterForm" method="GET" action="?" class="form-search">
	<div class="well">
		<div class="pull-right">
			<input id="dateFrom" name="dateFrom" class="datepicker" placeholder="<fmt:message key="general.date.from" />" value='<fmt:formatDate value="${ dateFrom }" pattern="dd/MM/yyyy"/>' />
			<input id="dateTo" name="dateTo" class="datepicker" placeholder="<fmt:message key="general.date.to" />" value="<fmt:formatDate value="${ dateTo }" pattern="dd/MM/yyyy"/>" />
			<input type="submit" class="btn btn-primary" value="<fmt:message key='general.search' />" />
		</div>
	</div>

	<display:table id="${id}" name="${rows}" size="${size}" pagesize="10"
		export="false" class="table table-striped table-bordered table-condensed"
		requestURI="?" sort="external" partialList="true">
		<display:column titleKey="job.number" sortable="false">
			<a class="iframe" href="${pageContext.request.contextPath}/job/popupview/${job.id}"><i class="fa-icon-fullscreen"></i></a>
			<c:out value="${ job.jobNumber }"/>
		</display:column>
		<display:column titleKey="job.price" sortable="false">
			<c:if test="${ job.price != null }">
				<fmt:formatNumber minFractionDigits="2" value="${ job.price }"/>
			</c:if>
		</display:column>
		<display:column titleKey="job.jobDate" sortable="false">
			<c:if test="${ job.jobDate != null }">
				<fmt:formatDate value="${ job.jobDate }" pattern="dd/MM/yyyy" />
			</c:if>
		</display:column>
	</display:table>
	
</form>

<commons:widget-footer />

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorbox.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.colorbox-min.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	$(".iframe").colorbox({iframe:true, width:"80%", height:"80%"});
});

function gotoEdit(){
	document.location.href = "${pageContext.request.contextPath}/customer/edit/${customer.id}";
}

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/customer/";
}

</script>


<commons:footer />