<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:notification-message/>

<commons:widget-header widgetLogo="barcode" widgetLabel="vendor.view" />

<ul class="nav tab-menu nav-tabs" id="myTab">
	<li><a href="#details">Details</a></li>
	<li><a href="#history">History</a></li>
</ul>
<div id="myTabContent" class="tab-content">	
	<div class="tab-pane" id="details">	
		<div class="row-fluid">
			<div class="span3">
				<div class="control-group">
					<label class="control-label">
						<h3><fmt:message key="vendor.company.name"/></h3>
					</label>
					<div class="controls">
						<c:out value="${ vendor.companyName }"/>
					</div>
				</div>
			</div>
			<div class="span3">
				<div class="control-group">
					<label class="control-label" >
						<h3><fmt:message key="vendor.contact.person"/></h3>
					</label>
					<div class="controls">
						<c:out value="${ vendor.contactPerson }"/>
					</div>
				</div>
			</div>
			<div class="span3">
				<div class="control-group ">
					<label class="control-label" >
						<h3><fmt:message key="vendor.contact.number"/></h3>
					</label>
					<div class="controls">
						<c:out value="${ vendor.contactNumber }"/>
					</div>
				</div>
			</div>
			<div class="span3">
				<div class="control-group">
					<label class="control-label" >
						<h3><fmt:message key="vendor.office.number"/></h3>
					</label>
					<div class="controls">
						<c:out value="${ vendor.officeNumber }"/>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group ">
					<label class="control-label" >
						<h3><fmt:message key="vendor.address"/></h3>
					</label>
					<div class="controls">
						<c:out value="${ vendor.address }"/>
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group ">
					<label class="control-label" >
						<h3><fmt:message key="general.remark"/></h3>
					</label>
					<div class="controls">
						<c:out value="${ vendor.remark }"/>
					</div>
				</div>
			</div>
		</div>
		
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
	document.location.href = "${pageContext.request.contextPath}/vendor/";
}

</script>

<commons:widget-footer />
<commons:footer />