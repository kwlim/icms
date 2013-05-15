<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<c:set var="widgetLabel" value="po.new"/>
<c:if test="${ !empty purchaseOrder.id }">
	<c:set var="widgetLabel" value="po.edit"/>
</c:if>

<commons:notification-message/>

<commons:widget-header widgetLogo="barcode" widgetLabel="${ widgetLabel }" />

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
					<form:select path="vendor.id" cssClass="input-xlarge" >
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
			<div class="control-group">
				<label class="control-label" for="remark"> 
					<fmt:message key="general.remark" />
				</label>
				<div class="controls">
					<form:textarea path="remark" class="input-xlarge" rows="3" />
				</div>
			</div>
		</div>
	</div>
	
	<div class="form-actions">
		<button type="submit" class="btn btn-primary"><fmt:message key="general.save"/></button>
		<button type="button" class="btn" onclick="cancel()"><fmt:message key="general.cancel"/></button>
	</div>

</form:form>
<commons:widget-footer />

<c:if test="${ !empty purchaseOrder.id }">
<commons:widget-header widgetLogo="barcode" widgetLabel="po.addItem" />

<form:form class="form-horizontal" commandName="stockOrder" method="POST"
	action="${pageContext.request.contextPath}/po/addItem">
	<form:hidden path="purchaseOrder.id"/>
	
	<div class="row-fluid">
		<div class="span3">
			<form:select path="item.category.id" onchange="javascript:reflectItem()">
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
		<div class="span3 control-group <form:errors path="item.id" cssClass="error">error</form:errors>">
			<form:select path="item.id">
				<form:option value="">Please select an item</form:option>
				<form:options items="${ validationReloadFailItemList }" itemLabel="name" itemValue="id" />
			</form:select>
		</div>
		<div class="span3">
			<button class="btn btn-primary">Add Item</button>
		</div>
	</div>
</form:form>

<form:form class="form-horizontal" commandName="purchaseOrder" method="POST"
	action="${pageContext.request.contextPath}/po/saveItem">
	<form:hidden path="id"/>
	<form:hidden path="vendor.id"/>
	<form:hidden path="poNumber"/>
	<form:hidden path="poDate"/>
	<form:hidden path="price"/>
	<form:hidden path="remark"/>
	
	<div class="well well-small">
	<div class="row-fluid">
		<div class="span5">
			<h3>Item Name</h3>
		</div>
		<div class="span2">
			<h3>Unit</h3>
		</div>
		<div class="span2">
			<h3>Price Per Unit</h3>
		</div>
		<div class="span1">
			<div class="pull-right"><h3>Total</h3></div>
		</div>
		<div class="span2">
			&nbsp;
		</div>
	</div>
	</div>
	
	<c:choose>
	<c:when test="${ empty purchaseOrder.stockOrderList }">
	<div class="row-fluid">
		<div class="span12 text-center">
			No item found.
		</div>
	</div>
	</c:when>
	<c:otherwise>
	
	
	<c:forEach items="${ purchaseOrder.stockOrderList }" var="tempStockOrder" varStatus="status">
	
	<div class="row-fluid">
		<div class="span5">
			<form:hidden path="stockOrderList[${status.index }].id" />
			<form:hidden path="stockOrderList[${status.index }].item.id" />
			<form:hidden path="stockOrderList[${status.index }].purchaseOrder.id" />
			<form:hidden path="stockOrderList[${status.index }].item.name" />
			<form:hidden path="stockOrderList[${status.index }].item.code" />
			<c:out value="${ tempStockOrder.item.name }"/> (<c:out value="${ tempStockOrder.item.code }"/>)
		</div>
		<div class="span2 control-group <form:errors path="stockOrderList[${status.index }].quantity" cssClass="error">error</form:errors>">
			<form:input path="stockOrderList[${status.index }].quantity" cssClass="input-small" onchange="updateItemTotal()"/>
			<form:errors path="stockOrderList[${status.index }].quantity" cssClass="help-inline"/>
		</div>
		<div class="span2 control-group <form:errors path="stockOrderList[${status.index }].unitPrice" cssClass="error">error</form:errors>">
			<form:input path="stockOrderList[${status.index }].unitPrice" cssClass="input-small" onchange="updateItemTotal()"/>
			<form:errors path="stockOrderList[${status.index }].unitPrice" cssClass="help-inline"/>
		</div>
		<div class="span1">
			<div id="total${ status.index }" class="pull-right">150.00</div>
		</div>
		<div class="span2">
			<div class="pull-right">
			<c:url var="delUrl" value="/po/deleteItem/${purchaseOrder.id}/${ tempStockOrder.id }"/>
			<a href="${ delUrl }" class="btn btn-danger" data-rel="tooltip" data-original-title="<fmt:message key='general.delete'/>" onclick="return deleteRecords()">
				<i class="halflings-icon remove halflings-icon" ></i>
			</a>
			</div>
		</div>
	</div>
	
	</c:forEach>
	<div class="row-fluid">
		<div class="span5"></div>
		<div class="span2"></div>
		<div class="span2"><h3>Grand Total</h3></div>
		<div class="span1">
			<div id="grandTotal" class="pull-right">300.00</div>
		</div>
		<div class="span2"></div>
	</div>
	
	</c:otherwise>
	</c:choose>
	
	<div class="form-actions">
		<button type="submit" class="btn btn-primary"><fmt:message key="general.save"/></button>
		<button type="button" class="btn" onclick="cancel()"><fmt:message key="general.cancel"/></button>
	</div>
</form:form>

<commons:widget-footer />

</c:if>

<script type="text/javascript">

$(document).ready(function() {
	updateItemTotal();
});

function updateItemTotal(){
	var totalItems = ${fn:length(purchaseOrder.stockOrderList)};
	var grandTotal = 0;
	for(var i=0; i < totalItems; i++){
		var itemsQuantity = "#stockOrderList"+i+"\\.quantity";
		var itemsUnitPrice = "#stockOrderList"+i+"\\.unitPrice";
		var totalItemPrice = '#total'+i;
		
		var totalFloat = $(itemsQuantity).val() * $(itemsUnitPrice).val();
		grandTotal += totalFloat;
		$(totalItemPrice).html(totalFloat.toFixed(2));
		//alert($(itemsQuantity).val() + "|" + $(itemsUnitPrice).val() + "|" + $(totalItemPrice).html());
	}
	
	if(grandTotal != '' || grandTotal != 'NaN'){
		$("#price").val(grandTotal);
	}
	$("#grandTotal").html(grandTotal.toFixed(2));
}

function reflectItem(){
	
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

function deleteRecords() {
    if (confirm('<fmt:message key="general.delete.confirmation"/>')) {
    	return true;
    }
    
    return false;
}

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/po/";
}
</script>


<commons:footer />