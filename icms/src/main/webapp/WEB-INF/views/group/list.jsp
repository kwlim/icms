<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />
<commons:widget-header widgetLogo="bookmark" widgetLabel="user.group" />

<c:if test="${!empty message}">
	<div class="alert alert-success">
		<fmt:message key='${message}' />
	</div>
</c:if>
<c:if test="${!empty error}">
	<div class="alert alert-error">
		<fmt:message key='${error}' />
	</div>
</c:if>

<form id="filterForm" method="GET" action="?" class="form-search">
	<div class="filterContainer well">
		<span class="filterCell"> 
			<label for="name"><fmt:message key="general.name" /></label> 
			<input id="name" name="name" class="input-medium" value="${name}" />
		</span> 
		<span class="filterSubmit"> 
			<input type="submit" class="btn btn-primary" value="<fmt:message key='general.search' />" />
		</span>
	</div>
	<div class="filter">
		<button type="submit" class="btn" onclick="deleteRecords()"><fmt:message key="general.delete"/></button>
		<button type="button" class="btn" onclick="newGroup()"><fmt:message key="general.new"/></button>
	</div>
	<display:table id="${id}" name="${rows}" size="${size}" pagesize="10"
		export="false" class="table table-striped table-bordered table-condensed"
		requestURI="?" sort="external" partialList="true">
		<display:column media="html" title="<input type='checkbox' id='selectall'/>" class="selectAll">
			<input name="id" type="checkbox" value="${group.id}" />
		</display:column>
		<display:column titleKey="general.name" sortable="true">
			<c:url var="editUrl" value="/group/edit/${ group.id }">
			</c:url>
			<a href="${ editUrl }"><c:out value="${ group.name }" /></a>
		</display:column>
		<display:column titleKey="general.lastUpdatedDate" sortable="true">
			<c:if test="${ group.modifiedDate != null }">
				<fmt:formatDate value="${ group.modifiedDate }" pattern="dd MMM yyyy hh:mm a" />
			</c:if>
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

function newGroup(){
	document.location.href = "${pageContext.request.contextPath}/group/new";
}

function deleteRecords() {
    if ($("[name=id]:checked").length > 0) {
        if (confirm('<fmt:message key="general.delete.confirmation"/>')) {
            $("#filterForm").attr("method", "POST");
            $("#filterForm").attr("action", "${pageContext.request.contextPath}/group/delete");
            return true;
        }
    }
}
</script>

<commons:footer />