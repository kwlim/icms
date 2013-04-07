<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header-login/>

<div class="login-box"> 
	<c:if test="${!empty message}">
	<div class="alert alert-error">
		<fmt:message key='${message}' />
	</div>
	</c:if>
	
	<div class="icons">
		<h2>ICMS</h2>
	</div>
	<h2>Enter details</h2>
	<form:form class="form-horizontal" commandName="userForm" action="${ pageContext.request.contextPath }/forgotPassword"  method="post">		
		<div class="input-prepend" title="Username">
			<span class="add-on"><i class="halflings-icon user"></i></span>
			<form:input path="username"/>
		</div>
		<div class="clearfix"></div>

		<div class="input-prepend" title="Password">
			<span class="add-on"><i class="halflings-icon email"></i></span>
			<form:input path="email"/>
		</div>
		<div class="clearfix"></div>
		
		<div class="button-login">
			<button type="submit" class="btn btn-primary"><fmt:message key="general.submit" /></button>
		</div>
		<div class="clearfix"></div>
	</form:form>
	<hr>
	<h3>You will received an email on your resetted password if the forgot password details are matched.</h3>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$("#username").focus();
});
</script>

<commons:footer-login />