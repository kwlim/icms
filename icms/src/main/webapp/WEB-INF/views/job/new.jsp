<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<c:set var="widgetLabel" value="job.new"/>
<c:if test="${ !empty jobOrder.id }">
	<c:set var="widgetLabel" value="job.edit"/>
</c:if>

<commons:notification-message/>

<commons:widget-header widgetLogo="barcode" widgetLabel="${ widgetLabel }" />

<form:form class="form-horizontal" commandName="jobOrder" method="POST"
	action="${pageContext.request.contextPath}/job/save/submit">
	
	<form:hidden path="id"/>
	
	<div class="row-fluid">
		<div class="span4">
			<div class="control-group <form:errors path="customer.id" cssClass="error">error</form:errors>">
				<label class="control-label" for="customer.id">
					<fmt:message key="customer.label"/>
					<span class="mandatory"><fmt:message key="general.mandatory"/></span>
				</label>
				<div class="controls">
					<form:input path="autoCompleteCarPlateNumber" cssClass="input-medium"/>
					<form:hidden path="customer.id" />
					<div><form:errors path="customer.id" cssClass="help-inline"/></div>
				</div>
			</div>
		</div>
		<div class="span4">
			<div class="control-group <form:errors path="millage" cssClass="error">error</form:errors>">
				<label class="control-label" for="millage">
					<fmt:message key="job.millage"/>
				</label>
				<div class="controls">
					<form:input path="millage" cssClass="input-medium"/>
					<div><form:errors path="millage" cssClass="help-inline"/></div>
				</div>
			</div>
		</div>
		<div class="span4">
			<div class="control-group <form:errors path="jobNumber" cssClass="error">error</form:errors>">
				<label class="control-label" for="jobNumber">
					<fmt:message key="job.number"/>
				</label>
				<div class="control-label">
					<form:hidden path="jobNumber" />
					<strong><c:out value="${ jobOrder.jobNumber }"/></strong>
				</div>
			</div>
		</div>
		
	</div>
	
	<div class="row-fluid">
		<div class="span4">
			<div class="control-group <form:errors path="jobDate" cssClass="error">error</form:errors>">
				<label class="control-label" for="jobDate">
					<fmt:message key="job.jobDate"/>
					<span class="mandatory"><fmt:message key="general.mandatory"/></span>
				</label>
				<div class="controls">
					<form:input path="jobDate" cssClass="datepicker input-medium"/>
					<div><form:errors path="jobDate" cssClass="help-inline"/></div>
				</div>
			</div>
		</div>
		<div class="span8">
			<label class="control-label" for="remark">
			<fmt:message key="general.remark"/>
		</label>
		<div class="controls">
			<form:textarea path="remark" class="input-xlarge" rows="3"/>
		</div>
		</div>
	</div>
	
	<div class="form-actions">
		<button type="submit" class="btn btn-primary"><fmt:message key="general.save"/></button>
		<button type="button" class="btn" onclick="cancel()"><fmt:message key="general.cancel"/></button>
	</div>

</form:form>
<commons:widget-footer />

<c:if test="${ !empty jobOrder.id }">
<commons:widget-header widgetLogo="barcode" widgetLabel="job.addItem" />

<form:form class="form-horizontal" commandName="jobItem" method="POST"
	action="${pageContext.request.contextPath}/job/addItem">
	<form:hidden path="jobOrder.id"/>
	
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

<form:form class="form-horizontal" commandName="jobOrder" method="POST"
	action="${pageContext.request.contextPath}/job/saveItem">
	<form:hidden path="id"/>
	<form:hidden path="customer.id"/>
	<form:hidden path="jobNumber"/>
	<form:hidden path="jobDate"/>
	<form:hidden path="price"/>
	<form:hidden path="remark"/>
	
	<div class="well well-small">
	<div class="row-fluid">
		<div class="span5">
			<h3><fmt:message key="job.item.name"/></h3>
		</div>
		<div class="span2">
			<h3><fmt:message key="job.item.stockPrice"/></h3>
		</div>
		<div class="span1">
			<h3><fmt:message key="job.item.unit"/></h3>
		</div>
		<div class="span1">
			<h3><fmt:message key="job.item.price"/></h3>
		</div>
		<div class="span1">
			<h3><fmt:message key="job.item.labour"/></h3>
		</div>
		<div class="span1">
			<div class="pull-right"><h3><fmt:message key="general.total"/></h3></div>
		</div>
		<div class="span1">
			&nbsp;
		</div>
	</div>
	</div>
	
	<c:choose>
	<c:when test="${ empty jobOrder.jobItemList }">
	<div class="row-fluid">
		<div class="span12 text-center">
			No item found.
		</div>
	</div>
	</c:when>
	<c:otherwise>
	
	<c:forEach items="${ jobOrder.jobItemList }" var="tempJobItem" varStatus="status">
	
	<div class="row-fluid">
		<div class="span5">
			<form:hidden path="jobItemList[${status.index }].id" />
			<form:hidden path="jobItemList[${status.index }].item.id" />
			<form:hidden path="jobItemList[${status.index }].jobOrder.id" />
			<form:hidden path="jobItemList[${status.index }].item.name" />
			<form:hidden path="jobItemList[${status.index }].item.code" />
			<c:out value="${ tempJobItem.item.name }"/> (<c:out value="${ tempJobItem.item.code }"/>)
		</div>
		<div class="span2 control-group <form:errors path="jobItemList[${status.index }].stockPrice" cssClass="error">error</form:errors>">
			
			<a href="javascript:toggleStockPrice('exp_${tempJobItem.id}')"><i class="halflings-icon plus" id="exp_${tempJobItem.id}_icon"></i></a>
			<span id="exp_${tempJobItem.id}" style="display: none">
			<form:input path="jobItemList[${status.index }].stockPrice" cssClass="input-mini" onchange="updateItemTotal()"/>
			</span>
			<form:errors path="jobItemList[${status.index }].stockPrice" cssClass="help-inline"/>
		</div>
		<div class="span1 control-group <form:errors path="jobItemList[${status.index }].unit" cssClass="error">error</form:errors>">
			<form:input path="jobItemList[${status.index }].unit" cssClass="input-mini" onchange="updateItemTotal()"/>
			<form:errors path="jobItemList[${status.index }].unit" cssClass="help-inline"/>
		</div>
		<div class="span1 control-group <form:errors path="jobItemList[${status.index }].markup" cssClass="error">error</form:errors>">
			<form:input path="jobItemList[${status.index }].markup" cssClass="input-mini" onchange="updateItemTotal()"/>
			<form:errors path="jobItemList[${status.index }].markup" cssClass="help-inline"/>
		</div>
		<div class="span1 control-group <form:errors path="jobItemList[${status.index }].labour" cssClass="error">error</form:errors>">
			<form:input path="jobItemList[${status.index }].labour" cssClass="input-mini" onchange="updateItemTotal()"/>
			<form:errors path="jobItemList[${status.index }].labour" cssClass="help-inline"/>
		</div>
		<div class="span1">
			<div id="total${ status.index }" class="pull-right">150.00</div>
		</div>
		<div class="span1">
			<div class="pull-right">
			<c:url var="delUrl" value="/job/deleteItem/${jobOrder.id}/${ tempJobItem.id }"/>
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
		<div class="span1"></div>
		<div class="span2"><h3><fmt:message key="job.discount"/></h3></div>
		<div class="span1 control-group <form:errors path="discount" cssClass="error">error</form:errors>">
			<form:input path="discount" cssClass="input-mini" onchange="updateItemTotal()"/>
			<form:errors path="discount" cssClass="help-inline"/>
		</div>
		<div class="span1"></div>
	</div>
	<div class="row-fluid">
		<div class="span5"></div>
		<div class="span2"></div>
		<div class="span1"></div>
		<div class="span2"><h3><fmt:message key="general.grandTotal"/></h3></div>
		<div class="span1">
			<div id="grandTotal" class="pull-right">300.00</div>
		</div>
		<div class="span1"></div>
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
	initCustomerAutoComplete();
});

function initCustomerAutoComplete(){
	$('#autoCompleteCarPlateNumber').autocomplete({
	    source: function (request, response) {
	        $.getJSON("${pageContext.request.contextPath}/customer/json/list",
	        	{
					carPlateNumber: $("#autoCompleteCarPlateNumber").val()
				},
	        	function (data) {
	            	response($.map(data, function (value, key) {
	            		return {
		                    label: value.data,
		                    value: value.data,
		                    id: value.value
	                	};
	            	}));
	        	}
			);
	    },
	    minLength: 2,
	    delay: 100,
	    select: function( event, ui ) {
	        $('#customer\\.id').val(ui.item.id);
	    },
	});
}

function updateItemTotal(){
	var totalItems = ${fn:length(jobOrder.jobItemList)};
	var grandTotal = 0;
	for(var i=0; i < totalItems; i++){
		var itemsUnit = "#jobItemList"+i+"\\.unit";
		var itemsStockPrice = "#jobItemList"+i+"\\.stockPrice";
		var itemsMarkup = "#jobItemList"+i+"\\.markup";
		var itemsLabour = "#jobItemList"+i+"\\.labour";
		var totalItemPrice = '#total'+i;
		
		var totalFloat = $(itemsUnit).val() * $(itemsStockPrice).val();
		
		if($(itemsMarkup).val() != ''){
			totalFloat += parseFloat($(itemsMarkup).val());
		}
		
		if($(itemsLabour).val() != ''){
			totalFloat += parseFloat($(itemsLabour).val());
		}
		
		grandTotal += totalFloat;
		
		$(totalItemPrice).html(totalFloat.toFixed(2));
	}
	
	if($("#discount").val() != ''){
		grandTotal -= $("#discount").val();
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

function toggleStockPrice(exp_id){
	var iconId = "#"+exp_id+"_icon";
	var targetSection = "#"+exp_id;
	
	$(targetSection).toggle();
	if($(targetSection).is(":visible")){
		$(iconId).attr('class', 'halflings-icon minus');
	}else{
		$(iconId).attr('class', 'halflings-icon plus');
	}
	
}

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/job/";
}
</script>


<commons:footer />