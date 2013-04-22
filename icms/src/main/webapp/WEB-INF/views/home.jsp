<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<h2><fmt:message key="general.welcome"/></h2>

<sec:authorize access="hasRole('ROLE_REPORT_VIEW')">
<div class="box">
	<div class="box-header">
		<h2>
			<i class="halflings-icon list-alt"></i><span class="break"></span><fmt:message key="report.daily.sales.chart"/>
		</h2>
	</div>
	<div class="box-content center">
		<div id="stackchart" class="center" style="height: 300px; "></div>
	</div>
	
</div>
</sec:authorize>


<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.flot.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	initChart();
});

function initChart(){
	
	var totalPriceData = [];
	var totalEarningData = [];
	var ticksData = [];
	
	<c:forEach var="tempPrice" items="${priceMap}" varStatus="status">
		totalPriceData.push([<c:out value="${ status.index }"/>, <c:out value="${ tempPrice.value }"/>]);
		ticksData.push([<c:out value="${ status.index }"/>, '<c:out value="${ tempPrice.key }"/>']);
	</c:forEach>
	<c:forEach var="tempEarning" items="${earningMap}" varStatus="status">
	totalEarningData.push([<c:out value="${ status.index }"/>, <c:out value="${ tempEarning.value }"/>]);
	</c:forEach>
	
	if($("#stackchart").length)
	{
		
		var data = [{
				data: totalPriceData, 
				color: '#FABB3D',
				label: 'Total Price',
				lines: { show: true, fill: true},
				points: { show: true}
			}
			,{
				data: totalEarningData, 
				color: '#2FABE9',
				label: 'Total Earning',
				lines: { show: true, fill: true},
				points: { show: true}
			}
		];
		
		var options = {
			xaxis: { ticks:ticksData},
			grid: {hoverable: true}
		};
		
		$.plot($("#stackchart"), data, options);
		
		function showTooltip(x, y, contents) {
			$("<div id='tooltip'>" + contents + "</div>").css({
				position: "absolute",
				display: "none",
				top: y - 30,
				left: x,
				'z-index': 60,
				border: "1px solid #fdd",
				padding: "2px",
				"background-color": "#fee",
				opacity: 0.80
			}).appendTo("body").fadeIn(200);
		}
		
		var previousPoint = null;
		$("#stackchart").bind("plothover", function (event, pos, item) {
			
			if (item) {
				if (previousPoint != item.dataIndex) {
		               previousPoint = item.dataIndex;
				$("#tooltip").remove();
				var x = item.datapoint[0],
				y = item.datapoint[1];
				
				showTooltip(item.pageX, item.pageY, y);
				}
			} else {
				$("#tooltip").remove();
				previousPoint = null;  
			}
		});
		
	}
	
}


</script>

<commons:footer />