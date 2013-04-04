<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:notification-message/>

<commons:widget-header widgetLogo="shopping-cart" widgetLabel="item.view" />

	<div class="row-fluid">
		<div class="span3">
			<div class="control-group">
				<label class="control-label">
					<h3><fmt:message key="item.code" /></h3>
				</label>
				<div class="controls">
					<c:out value="${ item.code }" />
				</div>
			</div>
		</div>
		<div class="span3">
			<div class="control-group">
				<label class="control-label">
					<h3><fmt:message key="general.name" /></h3>
				</label>
				<div class="controls">
					<c:out value="${ item.name }" />
				</div>
			</div>
		</div>
		<div class="span3">
			<div class="control-group ">
				<label class="control-label">
					<h3><fmt:message key="item.category.label" /></h3>
				</label>
				<div class="controls">
					<c:out value="${ item.category.name }" />
				</div>
			</div>
		</div>
		<div class="span3">
			<div class="control-group">
				<label class="control-label">
					<h3><fmt:message key="item.brand.label" /></h3>
				</label>
				<div class="controls">
					<c:out value="${ item.brand.name }" />
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<div class="control-group ">
				<label class="control-label">
					<h3><fmt:message key="general.remark" /></h3>
				</label>
				<div class="controls">
					<c:out value="${ item.remark }" />
				</div>
			</div>
		</div>
	</div>

<commons:widget-footer />

<commons:widget-header widgetLogo="shopping-cart" widgetLabel="item.stockCheck" />

<form id="filterForm" method="GET" action="?" class="form-search">
	<div class="well">
		<div class="pull-right">
			<select id="monthFrom" name="monthFrom" class="input-small">
				<option value="">Filter by month</option>
				<c:forEach var="month" items="${ monthList }">
					<c:choose>
						<c:when test="${ month eq monthFrom }">
							<option value="${ month }" selected="selected"><fmt:message key="general.month.${ month }"/></option>
						</c:when>
						<c:otherwise>
							<option value="${ month }"><fmt:message key="general.month.${ month }"/></option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<input id="yearFrom" name="yearFrom" class="input-small" value="${ yearFrom }">
			- 
			<select id="monthTo" name="monthTo" class="input-small">
				<option value="">Filter by month</option>
				<c:forEach var="month" items="${ monthList }">
					<c:choose>
						<c:when test="${ month eq monthTo }">
							<option value="${ month }" selected="selected"><fmt:message key="general.month.${ month }"/></option>
						</c:when>
						<c:otherwise>
							<option value="${ month }"><fmt:message key="general.month.${ month }"/></option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<input id="yearTo" name="yearTo" class="input-small" value="${ yearTo }">
			
			<input type="submit" class="btn btn-primary" value="<fmt:message key='general.search' />" />
			
		</div>
	</div>

	<table id="item"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th class="header">Date</th>
				<th class="header">Description</th>
				<th class="header" style="width: 5em;">BF</th>
				<th class="header" style="width: 5em;">Purchase</th>
				<th class="header" style="width: 5em;">Sold</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="stockCheck" items="${ stockCheckList }" varStatus="status">
			<tr >
				<td><fmt:formatDate value="${ stockCheck.date }" pattern="dd/MM/yyyy" /></td>
				<td>
					<c:out value="${ stockCheck.description }"/>
					<c:if test="${ stockCheck.type == scTypePurchase }">
						@RM <fmt:formatNumber minFractionDigits="2" value="${ stockCheck.price }"/>
					</c:if>
				</td>
				<td><c:if test="${ stockCheck.type == scTypeBringForward }"><c:out value="${ stockCheck.unit }"/></c:if></td>
				<td><c:if test="${ stockCheck.type == scTypePurchase }"><c:out value="${ stockCheck.unit }"/></c:if></td>
				<td><c:if test="${ stockCheck.type == scTypeSold }"><c:out value="${ stockCheck.unit }"/></c:if></td>
			</tr>
		</c:forEach>
		</tbody>
		
		<c:if test="${ empty stockCheckList }">
			<tbody>
				<tr >
					<td colspan="5">No Stock Check</td>
				</tr>
			</tbody>
		</c:if>
	</table>
</form>

<commons:widget-footer />

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