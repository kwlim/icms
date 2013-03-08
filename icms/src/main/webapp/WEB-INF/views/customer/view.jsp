<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:widget-header widgetLogo="barcode" widgetLabel="customer.view" />

<ul class="nav tab-menu nav-tabs" id="myTab">
	<li><a href="#details">Details</a></li>
	<li><a href="#history">History</a></li>
</ul>
<div id="myTabContent" class="tab-content">	
	<div class="tab-pane" id="details">	
		<commons:notification-message/>
		<table class="table table-bordered table-striped">
			<tr>
				<td><fmt:message key="customer.carPlateNumber"/></td>
				<td>
					<label class="radio">${customer.carPlateNumber }</label>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="general.name"/></td>
				<td>
					<label class="radio">${customer.name }</label>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="customer.contact.number"/></td>
				<td>
					<label class="radio">${customer.contactNumber }</label>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="customer.address"/></td>
				<td>
					<label class="radio">${customer.address }</label>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="general.remark"/></td>
				<td>
					<label class="radio">${customer.remark }</label>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<button type="button" class="btn btn-primary"  onclick="gotoEdit();"><fmt:message key="general.edit"/></button>
					<button type="button" class="btn" onclick="cancel();"><fmt:message key="general.cancel"/></button>
				</td>
			</tr>
		</table>
	</div>
	<div class="tab-pane" id="history">
		fedfew	
	</div>
</div>
<script type="text/javascript">

function gotoEdit(){
	document.location.href = "${pageContext.request.contextPath}/customer/edit/${customer.id}";
}

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/customer/list";
}

</script>

<commons:widget-footer />
<commons:footer />