<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:widget-header widgetLogo="barcode" widgetLabel="vendor.view" />

<ul class="nav tab-menu nav-tabs" id="myTab">
	<li><a href="#details">Details</a></li>
	<li><a href="#history">History</a></li>
</ul>
<div id="myTabContent" class="tab-content">	
	<div class="tab-pane" id="details">	
		<commons:notification-message/>
		<table class="table table-bordered table-striped">
			<tr>
				<td><fmt:message key="vendor.company.name"/></td>
				<td>
					<label class="radio">${vendor.companyName }</label>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="vendor.contact.person"/></td>
				<td>
					<label class="radio">${vendor.contactPerson }</label>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="vendor.contact.number"/></td>
				<td>
					<label class="radio">${vendor.contactNumber }</label>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="vendor.address"/></td>
				<td>
					<label class="radio">${vendor.address }</label>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="general.remark"/></td>
				<td>
					<label class="radio">${vendor.remark }</label>
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
	document.location.href = "${pageContext.request.contextPath}/vendor/edit/${vendor.id}";
}

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/vendor/list";
}

</script>

<commons:widget-footer />
<commons:footer />