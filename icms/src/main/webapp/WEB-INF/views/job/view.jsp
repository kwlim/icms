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


<commons:widget-header widgetLogo="barcode" widgetLabel="${ widgetLabel }" includeAction="print.jsp"/>

	<div id="myTabContent" class="tab-content">	
		<div class="row-fluid">
			<div class="span3">
				<div class="control-group">
					<label class="control-label">
						<h3><fmt:message key="customer.label"/></h3>
					</label>
					<div class="controls">
						<c:out value="${jobOrder.customer.carPlateNumber }"/>
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
						<fmt:formatDate value="${ jobOrder.jobDate }" pattern="dd MMM yyyy" />
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
				<th class="header">Unit</th>
				<th class="header" style="text-align: right;">Price Per Unit</th>
				<th class="header" style="text-align: right;">Price</th>
				<th class="header" style="text-align: right;">Labour</th>
				<th class="header" style="text-align: right; width: 10em">Total</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="jobItem" items="${ jobItemList }" varStatus="status">
				<tr >
					<td ><c:out value="${ jobItem.item.name }"/> (<c:out value="${ jobItem.item.code }"/>)</td>
					<td id="unit"><c:out value="${ jobItem.unit }"/></td>
					<td id="markup" style="text-align: right;"><fmt:formatNumber minFractionDigits="2" value="${ (jobItem.stockPrice * jobItem.unit +  jobItem.markup)/jobItem.unit }"/></td>
					<td id="markup" style="text-align: right;"><fmt:formatNumber minFractionDigits="2" value="${ (jobItem.stockPrice * jobItem.unit) +  jobItem.markup }"/></td>
					<td id="labour" style="text-align: right;"><fmt:formatNumber minFractionDigits="2" value="${ jobItem.labour }"/></td>
					<td id="total" style="text-align: right;"><fmt:formatNumber minFractionDigits="2" value="${ (jobItem.stockPrice * jobItem.unit) +  jobItem.markup + jobItem.labour}"/></td>
				</tr>
			</c:forEach>
				<tr >
					<td colspan="5" style="text-align: right;"><b>Grand Total</b></td>
					<td style="text-align: right;"><b><fmt:formatNumber minFractionDigits="2" value="${ jobOrder.price }"/></b></td>
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

