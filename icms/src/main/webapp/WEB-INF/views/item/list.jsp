<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />
<commons:widget-header widgetLogo="barcode" widgetLabel="item.label" />

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
			<label for="name"><fmt:message key="item.nameOrCode" /></label> 
			<input id="name" name="name" class="input-medium" value="${name}" />
		</span> 
		<span class="filterCell"> 
			<label for="group"><fmt:message key="item.category.label" /></label> 
			<select name="categoryId">
				<option value=""/>
				<c:forEach var="category" items="${ allCategoryList }">
					<c:choose>
						<c:when test="${ categoryId eq category.id }">
							<option value="${ category.id }" selected="selected"><c:out value="${ category.name }"/></option>
						</c:when>
						<c:otherwise>
							<option value="${ category.id }"><c:out value="${ category.name }"/></option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</span>
		<span class="filterSubmit"> 
			<input type="submit" class="btn btn-primary" value="<fmt:message key='general.search' />" />
		</span>
	</div>
	<div class="span6">
		<button type="submit" class="btn btn-danger" onclick="return deleteRecords()">
			<i class="halflings-icon trash halflings-icon"></i> <fmt:message key="general.delete"/>
		</button>
		<button type="button" class="btn btn-info" onclick="newItem()">
			<i class="halflings-icon edit halflings-icon"></i>  <fmt:message key="general.new"/>
		</button>
	</div>
	<display:table id="${id}" name="${rows}" size="${size}" pagesize="10"
		export="false" class="table table-striped table-bordered table-condensed"
		requestURI="?" sort="external" partialList="true">
		<display:column media="html" title="<input type='checkbox' id='selectall'/>" class="selectAll">
			<input id="id" name="id" type="checkbox" value="${item.id}" />
		</display:column>
		<display:column titleKey="item.code" sortable="true">
			<c:url var="editUrl" value="/item/edit/${ item.id }">
			</c:url>
			<a href="${ editUrl }"><c:out value="${ item.code }" /></a>
		</display:column>
		<display:column titleKey="general.name" property="name" sortable="true"/>
		<display:column titleKey="item.lowAmountNotif" property="lowAmountNotif" sortable="true"/>
		<display:column titleKey="item.category.label" property="category.name" sortable="true"/>
		<display:column titleKey="general.lastUpdatedDate" sortable="true">
			<c:if test="${ item.modifiedDate != null }">
				<fmt:formatDate value="${ item.modifiedDate }" pattern="dd/MM/yyyy hh:mm a" />
			</c:if>
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

function newItem(){
	document.location.href = "${pageContext.request.contextPath}/item/new";
}

function deleteRecords() {
    if ($("[name=id]:checked").length > 0) {
        if (confirm('<fmt:message key="general.delete.confirmation"/>')) {
            $("#filterForm").attr("method", "POST");
            $("#filterForm").attr("action", "${pageContext.request.contextPath}/item/delete");
            return true;
        }
    }
    return false;
}
</script>
<commons:widget-footer />
<commons:footer />