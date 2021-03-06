<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:notification-message/>

<commons:widget-header widgetLogo="barcode" widgetLabel="vendor.view.details" />

<div class="row-fluid">
	<div class="span3">
		<div class="control-group">
			<label class="control-label">
				<h3>
					<fmt:message key="vendor.company.name" />
				</h3>
			</label>
			<div class="controls">
				<c:out value="${ vendor.companyName }" />
			</div>
		</div>
	</div>
	<div class="span3">
		<div class="control-group">
			<label class="control-label">
				<h3>
					<fmt:message key="vendor.contact.person" />
				</h3>
			</label>
			<div class="controls">
				<c:out value="${ vendor.contactPerson }" />
			</div>
		</div>
	</div>
	<div class="span3">
		<div class="control-group ">
			<label class="control-label">
				<h3>
					<fmt:message key="vendor.contact.number" />
				</h3>
			</label>
			<div class="controls">
				<c:out value="${ vendor.contactNumber }" />
			</div>
		</div>
	</div>
	<div class="span3">
		<div class="control-group">
			<label class="control-label">
				<h3>
					<fmt:message key="vendor.office.number" />
				</h3>
			</label>
			<div class="controls">
				<c:out value="${ vendor.officeNumber }" />
			</div>
		</div>
	</div>
</div>

<div class="row-fluid">
	<div class="span6">
		<div class="control-group ">
			<label class="control-label">
				<h3>
					<fmt:message key="vendor.address" />
				</h3>
			</label>
			<div class="controls">
				<c:out value="${ vendor.address }" />
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
				<c:out value="${ vendor.remark }" />
			</div>
		</div>
	</div>
</div>

<commons:widget-footer />

<commons:widget-header widgetLogo="barcode" widgetLabel="vendor.view.history" includeAction="newPo.jsp"/>

<form id="filterForm" method="GET" action="?" class="form-search">
	<div class="well">
		<div class="pull-right">
			<input id="dateFrom" name="dateFrom" class="datepicker input-small" placeholder="<fmt:message key="general.date.from" />" value='<fmt:formatDate value="${ dateFrom }" pattern="dd/MM/yyyy"/>' />
			<input id="dateTo" name="dateTo" class="datepicker input-small" placeholder="<fmt:message key="general.date.to" />" value="<fmt:formatDate value="${ dateTo }" pattern="dd/MM/yyyy"/>" />
			<input type="submit" class="btn btn-primary" value="<fmt:message key='general.search' />" />
		</div>
	</div>
	
	<display:table id="${id}" name="${rows}" size="${size}" pagesize="10"
		export="false" class="table table-striped table-bordered table-condensed"
		requestURI="?" sort="external" partialList="true">
		<display:column titleKey="po.number" sortable="false">
			<c:out value="${ po.poNumber }"/>
		</display:column>
		<display:column titleKey="po.price" sortable="false">
			<c:if test="${ po.price != null }">
				<fmt:formatNumber minFractionDigits="2" value="${ po.price }"/>
			</c:if>
		</display:column>
		<display:column titleKey="po.date" sortable="false">
			<c:if test="${ po.poDate != null }">
				<fmt:formatDate value="${ po.poDate }" pattern="dd/MM/yyyy" />
			</c:if>
		</display:column>
		<display:column media="html"  titleKey="general.actions" sortable="false" class="tableAction">
			<c:url var="viewUrl" value="/po/popupview/${ po.id }"/>
			<a href="${ viewUrl }" class="btn btn-success iframe" data-rel="tooltip" data-original-title="<fmt:message key='general.view'/>">
				<i class="halflings-icon zoom-in"></i> 
			</a>
		</display:column>
	</display:table>
	
</form>

<commons:widget-footer />

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorbox.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.colorbox-min.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	$(".iframe").colorbox({iframe:true, width:"80%", height:"80%", rel: "nofollow"});
});

function gotoEdit(){
	document.location.href = "${pageContext.request.contextPath}/vendor/edit/${vendor.id}";
}

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/vendor/";
}

</script>


<commons:footer />