<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:notification-message/>

<commons:widget-header widgetLogo="globe" widgetLabel="customer.label" />

<form id="filterForm" method="GET" action="?" class="form-search">
	<div class="well">
		<div class="pull-right">
			<input id="name" placeholder="<fmt:message key="customer.carPlateNumberOrNameOrContact" />" name="name" class="input-large" value="${name}" />
			<input type="submit" class="btn btn-primary" value="<fmt:message key='general.search' />" />
		</div>
	</div>
	<div class="control-group">
		<sec:authorize access="hasRole('ROLE_CUSTOMER_DELETE')">
		<button type="submit" class="btn btn-danger" onclick="return deleteRecords()">
			<i class="halflings-icon trash"></i> <fmt:message key="general.delete"/>
		</button>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_CUSTOMER_ADD_EDIT')">
		<button type="button" class="btn btn-info" onclick="newRecords()">
			<i class="halflings-icon edit"></i>  <fmt:message key="general.new"/>
		</button>
		</sec:authorize>
	</div>
	<display:table id="${id}" name="${rows}" size="${size}" pagesize="10"
		export="false" class="table table-striped table-bordered table-condensed"
		requestURI="?" sort="external" partialList="true">
		<display:column media="html" title="<input type='checkbox' id='selectall'/>" class="selectAll">
			<input id="id" name="id" type="checkbox" value="${customer.id}" />
		</display:column>
		<display:column titleKey="customer.carPlateNumber" property="carPlateNumber" sortable="true"/>
		<display:column titleKey="general.name" property="name" sortable="true"/>
		<display:column titleKey="customer.contact.number" property="contactNumber" sortable="true"/>
		<display:column titleKey="general.lastUpdatedDate" sortable="true">
			<c:if test="${ customer.modifiedDate != null }">
				<fmt:formatDate value="${ customer.modifiedDate }" pattern="dd MMM yyyy hh:mm a" />
			</c:if>
		</display:column>
		<display:column media="html"  titleKey="general.actions" sortable="false" class="tableAction">
			<c:url var="viewUrl" value="/customer/view/${ customer.id }"/>
			<a href="${ viewUrl }" class="btn btn-success" data-rel="tooltip" data-original-title="<fmt:message key='general.view'/>">
				<i class="halflings-icon zoom-in"></i> 
			</a>
			<sec:authorize access="hasRole('ROLE_CUSTOMER_ADD_EDIT')">
			<c:url var="editUrl" value="/customer/edit/${ customer.id }"/>
			<a href="${ editUrl }" class="btn btn-info" data-rel="tooltip" data-original-title="<fmt:message key='general.edit'/>">
				<i class="halflings-icon edit" ></i>                                       
			</a>
			</sec:authorize>
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
	document.location.href = "${pageContext.request.contextPath}/customer/new";
}

function deleteRecords() {
    if ($("[name=id]:checked").length > 0) {
        if (confirm('<fmt:message key="general.delete.confirmation"/>')) {
            $("#filterForm").attr("method", "POST");
            $("#filterForm").attr("action", "${pageContext.request.contextPath}/customer/delete");
            return true;
        }
    }
    return false;
}
</script>
<commons:widget-footer />
<commons:footer />