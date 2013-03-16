<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />
<commons:widget-header widgetLogo="shopping-cart" widgetLabel="item.label" />

<commons:notification-message/>

<form id="filterForm" method="GET" action="?" class="form-search">
	<div class="filterContainer well">
		<span class="filterCell">
			<input id="name" placeholder="<fmt:message key="item.nameOrCode" />" name="name" class="input-medium" value="${name}" />
			<select name="categoryId">
				<option value="">Search by Category</option>
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
			<select name="brandId">
				<option value="">Search by Brand</option>
				<c:forEach var="brand" items="${ allBrandList }">
					<c:choose>
						<c:when test="${ brandId eq brand.id }">
							<option value="${ brand.id }" selected="selected"><c:out value="${ brand.name }"/></option>
						</c:when>
						<c:otherwise>
							<option value="${ brand.id }"><c:out value="${ brand.name }"/></option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</span> 
		<span class="filterSubmit"> 
			<input type="submit" class="btn btn-primary" value="<fmt:message key='general.search' />" />
		</span>
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
			<input id="id" name="id" type="checkbox" value="${item.id}" />
		</display:column>
		<display:column titleKey="item.code" property="code" sortable="true"/>
		<display:column titleKey="general.name" property="name" sortable="true"/>
		<display:column titleKey="item.category.label" property="category.name" sortable="true"/>
		<display:column titleKey="item.brand.new" property="brand.name" sortable="true"/>
		<display:column titleKey="item.lowAmountNotif" property="lowAmountNotif" sortable="true"/>
		<display:column titleKey="general.lastUpdatedDate" sortable="true">
			<c:if test="${ item.modifiedDate != null }">
				<fmt:formatDate value="${ item.modifiedDate }" pattern="dd/MM/yyyy hh:mm a" />
			</c:if>
		</display:column>
		<display:column media="html"  titleKey="general.actions" sortable="false">
			<c:url var="viewUrl" value="/item/view/${ item.id }"/>
			<a href="${ viewUrl }" class="btn btn-success" data-rel="tooltip" data-original-title="<fmt:message key='general.view'/>">
				<i class="halflings-icon zoom-in halflings-icon"></i> 
			</a>
			<c:url var="editUrl" value="/item/edit/${ item.id }"/>
			<a href="${ editUrl }" class="btn btn-info" data-rel="tooltip" data-original-title="<fmt:message key='general.edit'/>">
				<i class="halflings-icon edit halflings-icon" ></i>                                       
			</a>
		</display:column>
	</display:table>
</form>

<script type="text/javascript">

$(document).ready(function() {
	$(".datepicker").datepicker({dateFormat : 'dd/m/yy'});
	
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