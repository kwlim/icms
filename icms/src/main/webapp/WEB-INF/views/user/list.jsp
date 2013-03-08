<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />
<commons:widget-header widgetLogo="user" widgetLabel="user.label"/>

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
			<label for="username"><fmt:message key="general.username" /></label> 
			<input id="username" name="username" class="input-medium" value="${username}" />
		</span> 
		<span class="filterCell"> 
			<label for="firstname"><fmt:message key="user.firstname" /></label> 
			<input id="firstname" name="firstname" class="input-medium" value="${firstname}" />
		</span>
		<span class="filterCell"> 
			<label for="lastname"><fmt:message key="user.lastname" /></label> 
			<input id="lastname" name="lastname" class="input-medium" value="${lastname}" />
		</span>
		<span class="filterCell"> 
			<label for="group"><fmt:message key="user.group" /></label> 
			<select name="groupId">
				<option value=""/>
				<c:forEach var="group" items="${ allGroupList }">
					<c:choose>
						<c:when test="${ groupId eq group.id }">
							<option value="${ group.id }" selected="selected"><c:out value="${ group.name }"/></option>
						</c:when>
						<c:otherwise>
							<option value="${ group.id }"><c:out value="${ group.name }"/></option>
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
			<input name="id" type="checkbox" value="${user.id}" />
		</display:column>
		<display:column titleKey="general.name" property="username" sortable="true"/>
		<display:column titleKey="user.firstname" property="firstname" sortable="true"/>
		<display:column titleKey="user.lastname" property="lastname" sortable="true"/>
		<display:column titleKey="user.group" property="group.name" sortable="true"/>
		<display:column titleKey="user.status" sortable="true">
			<c:choose>
				<c:when test="${ user.status == 1 }">
					<span class="label label-success"><fmt:message key="user.status.${ user.status }"/></span>
				</c:when>
				<c:otherwise>
					<span class="label"><fmt:message key="user.status.${ user.status }"/></span>
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column titleKey="general.lastUpdatedDate" sortable="true">
			<c:if test="${ user.modifiedDate != null }">
				<fmt:formatDate value="${ user.modifiedDate }" pattern="dd/MM/yyyy hh:mm a" />
			</c:if>
		</display:column>
		<display:column media="html"  titleKey="general.actions" sortable="false">
			<c:url var="editUrl" value="/user/edit/${ user.id }"/>
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
	document.location.href = "${pageContext.request.contextPath}/user/new";
}

function deleteRecords() {
    if ($("[name=id]:checked").length > 0) {
        if (confirm('<fmt:message key="general.delete.confirmation"/>')) {
            $("#filterForm").attr("method", "POST");
            $("#filterForm").attr("action", "${pageContext.request.contextPath}/user/delete");
            return true;
        }
    }
    return false;
}
</script>

<commons:widget-footer />
<commons:footer />