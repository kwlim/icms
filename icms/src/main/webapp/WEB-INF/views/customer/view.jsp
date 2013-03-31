<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:notification-message/>

<commons:widget-header widgetLogo="globe" widgetLabel="customer.view" />

<ul class="nav tab-menu nav-tabs" id="myTab">
	<li><a href="#details">Details</a></li>
	<li><a href="#history">History</a></li>
</ul>
<div id="myTabContent" class="tab-content">	
	<div class="tab-pane" id="details">	
		
		<div class="row-fluid">
			<div class="span4">
				<div class="control-group">
					<label class="control-label">
						<h3><fmt:message key="customer.carPlateNumber"/></h3>
					</label>
					<div class="controls">
						<c:out value="${ customer.carPlateNumber }"/>
					</div>
				</div>
			</div>
			<div class="span4">
				<div class="control-group">
					<label class="control-label" >
						<h3><fmt:message key="general.name"/></h3>
					</label>
					<div class="controls">
						<c:out value="${ customer.name }"/>
					</div>
				</div>
			</div>
			<div class="span4">
				<div class="control-group ">
					<label class="control-label" >
						<h3><fmt:message key="customer.contact.number"/></h3>
					</label>
					<div class="controls">
						<c:out value="${ customer.contactNumber }"/>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group ">
					<label class="control-label" >
						<h3><fmt:message key="customer.address"/></h3>
					</label>
					<div class="controls">
						<c:out value="${ customer.address }"/>
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group ">
					<label class="control-label" >
						<h3><fmt:message key="general.remark"/></h3>
					</label>
					<div class="controls">
						<c:out value="${ customer.remark }"/>
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
	document.location.href = "${pageContext.request.contextPath}/customer/edit/${customer.id}";
}

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/customer/";
}

</script>

<commons:widget-footer />
<commons:footer />