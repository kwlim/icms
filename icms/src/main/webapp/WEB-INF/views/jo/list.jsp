<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:notification-message/>

<commons:widget-header widgetLogo="barcode" widgetLabel="jo.label" />

<form id="filterForm" method="GET" action="?" class="form-search">
	<div class="well">
		<div class="pull-right">
			<input id="joNumberCustomer" placeholder="<fmt:message key="jo.joNumberCustomer" />" name="joNumberCustomer" class="input-medium" value="${joNumberCustomer}" />
			<input id="joDateFrom" name="joDateFrom" class="datepicker" placeholder="<fmt:message key="jo.date.from" />" value='<fmt:formatDate value="${ joDateFrom }" pattern="dd/MM/yyyy"/>' />
			<input id="joDateTo" name="joDateTo" class="datepicker" placeholder="<fmt:message key="jo.date.to" />" value="<fmt:formatDate value="${ joDateTo }" pattern="dd/MM/yyyy"/>" />
			<input type="submit" class="btn btn-primary" value="<fmt:message key='general.search' />" />
		</div>
	</div>
	<div class="control-group">
		<button type="submit" class="btn btn-danger" onclick="return deleteRecords()">
			<i class="halflings-icon trash halflings-icon"></i> <fmt:message key="general.delete"/>
		</button>
		<button type="button" class="btn btn-info" onclick="newRecords()">
			<i class="halflings-icon edit halflings-icon"></i>  <fmt:message key="general.new"/>
		</button>
	</div>
	<display:table id="${id}" name="${rows}" size="${size}" pagesize="10"
		export="false" class="table table-striped table-bordered table-condensed"
		requestURI="?" sort="external" partialList="true">
		<display:column media="html" title="<input type='checkbox' id='selectall'/>" class="selectAll">
			<input id="id" name="id" type="checkbox" value="${jo.id}" />
		</display:column>
		<display:column titleKey="jo.number" property="jobNumber" sortable="true"/>
		<display:column titleKey="customer.label" property="customer.carPlateNumber" sortable="true"/>
		<display:column titleKey="jo.price" sortable="true">
			<c:if test="${ jo.price != null }">
				<fmt:formatNumber minFractionDigits="2" value="${ jo.price }"/>
			</c:if>
		</display:column>
		<display:column titleKey="general.lastUpdatedDate" sortable="true">
			<c:if test="${ jo.modifiedDate != null }">
				<fmt:formatDate value="${ jo.modifiedDate }" pattern="dd/MM/yyyy hh:mm a" />
			</c:if>
		</display:column>
		<display:column media="html"  titleKey="general.actions" sortable="false">
			<c:url var="viewUrl" value="/jo/view/${ jo.id }"/>
			<a href="${ viewUrl }" class="btn btn-success" data-rel="tooltip" data-original-title="<fmt:message key='general.view'/>">
				<i class="halflings-icon zoom-in halflings-icon"></i> 
			</a>
			<c:url var="editUrl" value="/jo/edit/${ jo.id }"/>
			<a href="${ editUrl }" class="btn btn-info" data-rel="tooltip" data-original-title="<fmt:message key='general.edit'/>">
				<i class="halflings-icon edit halflings-icon" ></i>                                       
			</a>
		</display:column>
	</display:table>
</form>

<script type="text/javascript">

$(document).ready(function() {
	initSelectAll();
});

function initSelectAll(){
	$('#selectall').click(function(){ 
	       var $checkbox = $(this).find(':checkbox');
	       $checkbox.attr('checked', !$checkbox.attr('checked'));
	       
	       if($('#selectall').is(":checked")){
	    	   var cb = $("#filterForm :checkbox").attr('checked', true);
	    	   $.uniform.update(cb);
	       }else{
	    	   var cb = $("#filterForm :checkbox").removeAttr('checked');
	    	   $.uniform.update(cb);
	       }
	       
	 });
}

function newRecords(){
	document.location.href = "${pageContext.request.contextPath}/jo/new";
}

function deleteRecords() {
    if ($("[name=id]:checked").length > 0) {
        if (confirm('<fmt:message key="general.delete.confirmation"/>')) {
            $("#filterForm").attr("method", "POST");
            $("#filterForm").attr("action", "${pageContext.request.contextPath}/jo/delete");
            return true;
        }
    }
    return false;
}
</script>
<commons:widget-footer />
<commons:footer />