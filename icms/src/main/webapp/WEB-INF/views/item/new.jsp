<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />
<commons:widget-header widgetLogo="shopping-cart" widgetLabel="item.new" />

<c:choose>
	<c:when test="${ campaignForm.edit }">
		<fmt:message key='campaign.edit' var="pageTitle"/>
	</c:when>
	<c:otherwise>
		<fmt:message key='campaign.new' var="pageTitle"/>		
	</c:otherwise>
</c:choose>

<commons:notification-message/>

<form:form class="form-horizontal" commandName="item" method="POST"
	action="${pageContext.request.contextPath}/item/save/submit">
	
	<form:hidden path="id"/>
	
	<div class="control-group <form:errors path="code" cssClass="error">error</form:errors>">
		<label class="control-label" for="code">
			<fmt:message key="item.code"/>
			<span class="mandatory"><fmt:message key="general.mandatory"/></span>
		</label>
		<div class="controls">
			<form:input path="code" cssClass="input-xlarge"/>
			<form:errors path="code" cssClass="help-inline"/>
		</div>
	</div>
	<div class="control-group <form:errors path="name" cssClass="error">error</form:errors>">
		<label class="control-label" for="name">
			<fmt:message key="general.name"/>
			<span class="mandatory"><fmt:message key="general.mandatory"/></span>
		</label>
		<div class="controls">
			<form:input path="name" cssClass="input-xlarge"/>
			<form:errors path="name" cssClass="help-inline"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="category">
			<fmt:message key="item.category.label"/>
		</label>
		<div class="controls">
			<form:select path="category.id" onchange="toggleNewCategory()" cssClass="input-xlarge">
				<form:option value=""></form:option>
				<form:option value="new">Create New...</form:option>
				<form:options items="${ allCategoryList }" itemLabel="name" itemValue="id"/>
			</form:select>
			
			<form:input path="newCategoryName" placeholder="New Category"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="brand">
			<fmt:message key="item.brand.label"/>
		</label>
		<div class="controls">
			<form:select path="brand.id" onchange="toggleNewBrand()" cssClass="input-xlarge">
				<form:option value=""></form:option>
				<form:option value="new">Create New...</form:option>
				<form:options items="${ allBrandList }" itemLabel="name" itemValue="id"/>
			</form:select>
			
			<form:input path="newBrandName" placeholder="New Brand"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="remark">
			<fmt:message key="general.remark"/>
		</label>
		<div class="controls">
			<form:textarea path="remark" cssClass="input-xxlarge" rows="5"/>
		</div>
	</div>
	<div class="control-group <form:errors path="lowAmountNotif" cssClass="error">error</form:errors>">
		<label class="control-label" for="lowAmountNotif">
			<fmt:message key="item.lowAmountNotif"/>
		</label>
		<div class="controls">
			<form:input path="lowAmountNotif" />
			<form:errors path="lowAmountNotif" cssClass="help-inline"/>
		</div>
	</div>
	<div class="form-actions">
		<button type="submit" class="btn btn-primary"><fmt:message key="general.submit"/></button>
		<button type="button" class="btn" onclick="cancel()"><fmt:message key="general.cancel"/></button>
	</div>

</form:form>

<script type="text/javascript">

$(document).ready(function() {
	$('#newCategoryName').hide();
	$('#newBrandName').hide();
});

function toggleNewCategory(){
	
	if($('#category\\.id').val() == 'new'){
		$('#newCategoryName').show();
	}else{
		$('#newCategoryName').hide();
	} 
}

function toggleNewBrand(){
	
	if($('#brand\\.id').val() == 'new'){
		$('#newBrandName').show();
	}else{
		$('#newBrandName').hide();
	} 
}

function cancel(){
	document.location.href = "${pageContext.request.contextPath}/item/";
}

</script>

<commons:widget-footer />
<commons:footer />