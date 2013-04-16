<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:notification-message/>

<commons:widget-header widgetLogo="shopping-cart" widgetLabel="item.stockCheck" />

<form id="filterForm" method="GET" action="?" class="form-search">
	<div class="well">
		<div class="pull-right">
			<input id="jobDateFrom" name="jobDateFrom" class="datepicker input-small" 
				placeholder="<fmt:message key="job.date.from" />" value='<fmt:formatDate value="${ jobDateFrom }" pattern="dd/MM/yyyy"/>' />
			<input id="jobDateTo" name="jobDateTo" class="datepicker input-small" 
				placeholder="<fmt:message key="job.date.to" />" value="<fmt:formatDate value="${ jobDateTo }" pattern="dd/MM/yyyy"/>" />			
			<input type="submit" class="btn btn-primary" value="<fmt:message key='general.search' />" />
		</div>
	</div>
	
	<table id="item" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th class="header">Category</th>
				<th class="header" style="width: 10em; text-align: right;">Total Earning</th>
				<th class="header" style="width: 10em; text-align: right;">Total Price</th>
				<th class="header" style="width: 10em; text-align: right;">%</th>
			</tr>
		</thead>
		
		<c:choose>
			<c:when test="${ ! empty result }">
				<c:set var="totalPrice" value="${ 0 }"/>
				<c:set var="totalEarning" value="${ 0 }"/>
				<tbody>
				
					<c:forEach var="categoryPrice" items="${ result }" varStatus="status">
						<tr >
							<td>
								<c:out value="${ categoryPrice.category.name }"/>
							</td>
							<td style="text-align: right;">
								<fmt:formatNumber minFractionDigits="2" value="${ categoryPrice.earning }"/>
								<c:set var="totalEarning" value="${ totalEarning + categoryPrice.earning }"/>
							</td>
							<td style="text-align: right;">
								<fmt:formatNumber minFractionDigits="2" value="${ categoryPrice.price }"/>
								<c:set var="totalPrice" value="${ totalPrice + categoryPrice.price }"/>
							</td>
							<td style="text-align: right;">
								<fmt:formatNumber minFractionDigits="2" value="${ categoryPrice.earning / categoryPrice.price * 100  }"/>
							</td>
						</tr>
					</c:forEach>
				
					<tr >
						<td style="text-align: right; padding-right: 20px">
							<h3>Total</h3>
						</td>
						<td style="text-align: right;">
							<fmt:formatNumber minFractionDigits="2" value="${ totalEarning }"/>
						</td>
						<td style="text-align: right;">
							<fmt:formatNumber minFractionDigits="2" value="${ totalPrice }"/>
						</td>
						<td style="text-align: right;">
							<fmt:formatNumber minFractionDigits="2" value="${ totalEarning / totalPrice * 100  }"/>
						</td>
					</tr>
				</tbody>
			
			</c:when>
			<c:otherwise>
				
				<tbody>
					<tr >
						<td colspan="5">No Data</td>
					</tr>
				</tbody>
				
			</c:otherwise>
		</c:choose>
		
	</table>
</form>

<commons:widget-footer />

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorbox.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.colorbox-min.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	
});


</script>

<commons:widget-footer />
<commons:footer />