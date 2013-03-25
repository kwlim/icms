<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<commons:notification-message/>

<commons:widget-header widgetLogo="tags" widgetLabel="item.category.label"/>

<form id="filterForm" method="GET" action="?" class="form-search">
	<div class="well">
		<div class="pull-right">
			<input id="name" placeholder="<fmt:message key="general.name" />" name="name" class="input-medium" value="${name}" />
			<input type="submit" value="<fmt:message key='general.search' />" />
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
			<input name="id" type="checkbox" value="${itemCategory.id}" />
		</display:column>
		<display:column titleKey="general.name" property="name" sortable="true"/>
		<display:column titleKey="general.lastUpdatedDate" sortable="true">
			<c:if test="${ itemCategory.modifiedDate != null }">
				<fmt:formatDate value="${ itemCategory.modifiedDate }" pattern="dd MMM yyyy hh:mm a" />
			</c:if>
		</display:column>
		<display:column media="html"  titleKey="general.actions" sortable="false">
			<c:url var="editUrl" value="/itemCategory/edit/${ itemCategory.id }"/>
			<a class="btn btn-success" href="${ editUrl }" data-rel="tooltip" data-original-title="<fmt:message key='general.view'/>">
				<i class="halflings-icon zoom-in halflings-icon"></i> 
			</a>
		</display:column>
	</display:table>
</form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.9.1.custom.min.js"></script>
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
	document.location.href = "${pageContext.request.contextPath}/itemCategory/new";
}

function deleteRecords() {
    if ($("[name=id]:checked").length > 0) {
        if (confirm('<fmt:message key="general.delete.confirmation"/>')) {
            $("#filterForm").attr("method", "POST");
            $("#filterForm").attr("action", "${pageContext.request.contextPath}/itemCategory/delete");
            return true;
        }
    }
    return false;
}
</script>

<commons:widget-footer />
<commons:footer />