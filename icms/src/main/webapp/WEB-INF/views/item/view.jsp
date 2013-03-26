<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />
<commons:widget-header widgetLogo="shopping-cart" widgetLabel="item.view" />

<ul class="nav tab-menu nav-tabs" id="myTab">
	<li><a href="#details">Details</a></li>
	<li><a href="#history">History</a></li>
</ul>
<div id="myTabContent" class="tab-content">	
	<div class="tab-pane" id="details">	
		<commons:notification-message/>
		<table class="table table-bordered table-striped">
			<tr>
				<td><fmt:message key="item.code"/></td>
				<td>
					<label class="radio">${item.code }</label>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="general.name"/></td>
				<td>
					<label class="radio">${item.name }</label>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="item.category.label"/></td>
				<td>
					<label class="radio">${item.category.name }</label>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="item.brand.label"/></td>
				<td>
					<label class="radio">${item.brand.name }</label>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="general.remark"/></td>
				<td>
					<label class="radio">${item.remark }</label>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="item.lowAmountNotif"/></td>
				<td>
					<label class="radio">${item.lowAmountNotif }</label>
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
	document.location.href = "${pageContext.request.contextPath}/item/edit/${item.id}";
}

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/item/";
}

</script>

<commons:widget-footer />
<commons:footer />