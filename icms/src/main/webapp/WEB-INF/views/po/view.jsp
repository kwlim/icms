<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<c:set var="widgetLabel" value="po.view"/>

<commons:notification-message/>

<commons:widget-header widgetLogo="barcode" widgetLabel="${ widgetLabel }" />

<div id="myTabContent" class="tab-content">	
	<div class="row-fluid">
		<div class="span3">
			<div class="control-group">
				<label class="control-label">
					<h3><fmt:message key="vendor.label"/></h3>
				</label>
				<div class="controls">
					<c:out value="${purchase.vendor.companyName }"/>
				</div>
			</div>
		</div>
		<div class="span3">
			<div class="control-group">
				<label class="control-label" >
					<h3><fmt:message key="po.number"/></h3>
				</label>
				<div class="controls">
					<c:out value="${ purchase.poNumber }"/>
				</div>
			</div>
		</div>
		<div class="span3">
			<div class="control-group ">
				<label class="control-label" >
					<h3><fmt:message key="po.date"/></h3>
				</label>
				<div class="controls">
					<c:out value="${ purchase.poDate }"/>
				</div>
			</div>
		</div>
		<div class="span3">
			<div class="control-group">
				<label class="control-label" >
					<h3><fmt:message key="general.remark" /></h3>
				</label>
				<div class="controls">
					<c:out value="${purchase.remark }"/>
				</div>
			</div>
		</div>
	</div>
</div>

<commons:widget-footer />
<commons:widget-header widgetLogo="barcode" widgetLabel="po.viewItem" />

	<table id="poOrderList"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th class="header">Item Name</th>
				<th class="header">Unit</th>
				<th class="header">Price Per Unit</th>
				<th class="header">Total</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="stockItem" items="${ stockList }" varStatus="status">
				<tr >
					<td><c:out value="${ stockItem.item.name }"/> (<c:out value="${ stockItem.item.code }"/>)</td>
					<td id="qty"><c:out value="${ stockItem.quantity }"/></td>
					<td id="unitPrice"><c:out value="${ stockItem.unitPrice }"/></td>
					<td id="total"><c:out value="${ stockItem.unitPrice *stockItem.quantity }"/></td>
				</tr>
			</c:forEach>
				<tr >
					<td  colspan="3" style="text-align: right;"><b>Grand Total</b></td>
					<td colspan="1"><b><c:out value="${ purchase.price }"/></b></td>
				</tr>
		</tbody>
		
		<c:if test="${ empty stockList }">
			<tbody>
				<tr >
					<td colspan="5">No item found.</td>
				</tr>
			</tbody>
		</c:if>
	</table>
<commons:widget-footer />

<script type="text/javascript">

</script>


<commons:footer />