<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:notification-message/>

<commons:widget-header widgetLogo="barcode" widgetLabel="po.label" />

<form id="filterForm" method="GET" action="?" class="form-search">
	<div class="well">
		<div class="pull-right">
			<input id="poNumber" placeholder="<fmt:message key="po.number" />" name="poNumber" class="input-medium" value="${poNumber}" />
			<select name="vendorId">
				<option value="">Search by Vendor</option>
				<c:forEach var="vendor" items="${ allVendorList }">
					<c:choose>
						<c:when test="${ vendorId eq vendor.id }">
							<option value="${ vendor.id }" selected="selected"><c:out value="${ vendor.companyName }"/></option>
						</c:when>
						<c:otherwise>
							<option value="${ vendor.id }"><c:out value="${ vendor.companyName }"/></option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<input id="poDateFrom" name="poDateFrom" class="datepicker" placeholder="<fmt:message key="po.date.from" />" value='<fmt:formatDate value="${ poDateFrom }" pattern="dd/MM/yyyy"/>' />
			<input id="poDateTo" name="poDateTo" class="datepicker" placeholder="<fmt:message key="po.date.to" />" value="<fmt:formatDate value="${ poDateTo }" pattern="dd/MM/yyyy"/>" />
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
			<input id="id" name="id" type="checkbox" value="${po.id}" />
		</display:column>
		<display:column titleKey="po.number" property="poNumber" sortable="true"/>
		<display:column titleKey="vendor.label" property="vendor.companyName" sortable="true"/>
		<display:column titleKey="po.price" sortable="true">
			<c:if test="${ po.price != null }">
				<fmt:formatNumber minFractionDigits="2" value="${ po.price }"/>
			</c:if>
		</display:column>
		<display:column titleKey="po.date" sortable="true">
			<c:if test="${ po.poDate != null }">
				<fmt:formatDate value="${ po.poDate }" pattern="dd MMM yyyy" />
			</c:if>
		</display:column>
		<display:column titleKey="general.lastUpdatedDate" sortable="true">
			<c:if test="${ po.modifiedDate != null }">
				<fmt:formatDate value="${ po.modifiedDate }" pattern="dd MMM yyyy hh:mm a" />
			</c:if>
		</display:column>
		<display:column media="html"  titleKey="general.actions" sortable="false" class="tableAction">
			<c:url var="viewUrl" value="/po/view/${ po.id }"/>
			<a href="${ viewUrl }" class="btn btn-success" data-rel="tooltip" data-original-title="<fmt:message key='general.view'/>">
				<i class="halflings-icon zoom-in halflings-icon"></i> 
			</a>
			<c:url var="editUrl" value="/po/edit/${ po.id }"/>
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
	document.location.href = "${pageContext.request.contextPath}/po/new";
}

function deleteRecords() {
    if ($("[name=id]:checked").length > 0) {
        if (confirm('<fmt:message key="general.delete.confirmation"/>')) {
            $("#filterForm").attr("method", "POST");
            $("#filterForm").attr("action", "${pageContext.request.contextPath}/po/delete");
            return true;
        }
    }
    return false;
}
</script>
<commons:widget-footer />
<commons:footer />