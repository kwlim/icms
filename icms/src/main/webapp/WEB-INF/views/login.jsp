<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header-login/>

<div class="login-box"> 
	<c:if test="${not empty param.error}">
	<div class="alert alert-error">
		<fmt:message key="${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}" />
	</div>
	</c:if>
	
	<div class="icons">
		<h2>ICMS</h2>
	</div>
	<h2>Login to your account</h2>
	<form class="form-horizontal" action="${ pageContext.request.contextPath }/submitlogin"  method="post">		
		<div class="input-prepend" title="Username">
			<span class="add-on"><i class="halflings-icon user"></i></span>
			<input class="input-large span10" name="j_username" id="username" type="text" placeholder="<fmt:message key="general.username"/>"/>
		</div>
		<div class="clearfix"></div>

		<div class="input-prepend" title="Password">
			<span class="add-on"><i class="halflings-icon lock"></i></span>
			<input class="input-large span10" name="j_password" id="password" type="password" placeholder="<fmt:message key="general.password"/>"/>
		</div>
		<div class="clearfix"></div>
		
		<!-- <label class="remember" for="remember"><input type="checkbox" id="remember" />Remember me</label> -->
		
		<div class="button-login">
			<button type="submit" class="btn btn-primary"><fmt:message key="general.login" /></button>
		</div>
		<div class="clearfix"></div>
	</form>
	<hr>
	<h3>Forgot Password?</h3>
	<p>
		No problem, <a href="#">click here</a> to get a new password.
	</p>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$("#username").focus();
});
</script>

<commons:footer-login />