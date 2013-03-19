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

<commons:widget-footer />

<commons:widget-header widgetLogo="barcode" widgetLabel="${ widgetLabel }" />

<form:form class="form-horizontal" commandName="stockOrder" method="POST"
	action="${pageContext.request.contextPath}/po/addItem">

<div class="row-fluid">
	<div class="span3">
		<form:select path="item.category.id" onchange="javascript:reflectItem()" data-placeholder="Choose a Country">
			<form:option value="">Filter by Category</form:option>
			<form:options items="${ allCategoryList }" itemLabel="name" itemValue="id" />
		</form:select>
	</div>
	<div class="span3">
		<form:select path="item.brand.id" onchange="javascript:reflectItem()">
			<form:option value="">Filter by Brand</form:option>
			<form:options items="${ allBrandList }" itemLabel="name" itemValue="id" />
		</form:select>
	</div>
	<div class="span3">
		<form:select path="item.id">
			<form:option value="">Please select an item</form:option>
		</form:select>
	</div>
	<div class="span3">
		<button>Submit</button>
	</div>
</div>

</form:form>


<commons:widget-footer />


<script type="text/javascript">

$(document).ready(function() {
	
});

function reflectItem(){
	//alert($("#item\\.category\\.id").val() + " | " + $("#item\\.brand\\.id").val());
	$.getJSON(
		"${pageContext.request.contextPath}/po/json/getItemList",
		{
			categoryId: $("#item\\.category\\.id").val(),
			brandId: $("#item\\.brand\\.id").val()
		},
		function(json){
			$("#item\\.id").empty();
			$("#item\\.id").append('<option value="">Please select an item</option>');
			for(var i=0; i<json.length; i++){
				$("#item\\.id").append('<option value="'+json[i].id+'">'+json[i].name+'</option>');
			}
		}
	);
}

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/po/";
}
</script>


<commons:footer />