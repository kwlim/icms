<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<c:choose>
	<c:when test="${ popup }">
		<commons:popupheader title="View Job Order"/>
	</c:when>
	<c:otherwise>
		<commons:header />
	</c:otherwise>
</c:choose>

<c:set var="widgetLabel" value="job.view"/>


<commons:widget-header widgetLogo="barcode" widgetLabel="${ widgetLabel }" />

	<div id="myTabContent" class="tab-content">	
		<div class="row-fluid">
			<div class="span3">
				<div class="control-group">
					<label class="control-label">
						<h3><fmt:message key="customer.label"/></h3>
					</label>
					<div class="controls">
						<c:out value="${jobOrder.customer.name }"/>
					</div>
				</div>
			</div>
			<div class="span3">
				<div class="control-group">
					<label class="control-label" >
						<h3><fmt:message key="job.number"/></h3>
					</label>
					<div class="controls">
						<c:out value="${ jobOrder.jobNumber }"/>
					</div>
				</div>
			</div>
			<div class="span3">
				<div class="control-group ">
					<label class="control-label" >
						<h3><fmt:message key="job.jobDate"/></h3>
					</label>
					<div class="controls">
						<c:out value="${ jobOrder.jobDate }"/>
					</div>
				</div>
			</div>
			<div class="span3">
				<div class="control-group">
					<label class="control-label" >
						<h3><fmt:message key="general.remark" /></h3>
					</label>
					<div class="controls">
						<c:out value="${jobOrder.remark }"/>
					</div>
				</div>
			</div>
		</div>
	</div>
<commons:widget-footer />


<commons:widget-header widgetLogo="barcode" widgetLabel="job.viewItem" />
	<table id="poOrderList"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th class="header">Item Name</th>
				<th class="header">Stock Price</th>
				<th class="header">Unit</th>
				<th class="header">Markup</th>
				<th class="header">Labour</th>
				<th class="header">Total</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="jobItem" items="${ jobItemList }" varStatus="status">
				<tr >
					<td><c:out value="${ jobItem.item.name }"/> (<c:out value="${ jobItem.item.code }"/>)</td>
					<td id="stockPrice"><fmt:formatNumber minFractionDigits="2" value="${ jobItem.stockPrice }"/></td>
					<td id="unit"><c:out value="${ jobItem.unit }"/></td>
					<td id="markup"><fmt:formatNumber minFractionDigits="2" value="${ jobItem.markup }"/></td>
					<td id="labour"><fmt:formatNumber minFractionDigits="2" value="${ jobItem.labour }"/></td>
					<td id="total"><fmt:formatNumber minFractionDigits="2" value="${ (jobItem.stockPrice * jobItem.unit) +  jobItem.markup + jobItem.labour}"/></td>
				</tr>
			</c:forEach>
				<tr >
					<td  colspan="5" style="text-align: right;"><b>Grand Total</b></td>
					<td colspan="1"><b><fmt:formatNumber minFractionDigits="2" value="${ jobOrder.price }"/></b></td>
				</tr>
		</tbody>
		
		<c:if test="${ empty jobItemList }">
			<tbody>
				<tr >
					<td colspan="5">No item found.</td>
				</tr>
			</tbody>
		</c:if>
	</table>
<commons:widget-footer />

<c:choose>
	<c:when test="${ popup }">
		<commons:popupfooter />
	</c:when>
	<c:otherwise>
		<commons:footer />
	</c:otherwise>
</c:choose>

