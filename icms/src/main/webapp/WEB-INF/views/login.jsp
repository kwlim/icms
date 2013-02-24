<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<commons:header />

<h1>
	<fmt:message key="general.login" />
</h1>

<c:if test="${not empty param.login_error}">
<div class="alert alert-error">
	<fmt:message key="${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}" />
</div>
</c:if>

<form class="forum-horizontal"
	action="${ pageContext.request.contextPath }/j_spring_security_check"
	method="POST">
	<div class="control-group">
		<label class="control-label" for="j_username"><fmt:message
				key="general.username" /></label>
		<div class="controls">
			<input type="text" name="j_username"
				placeholder="<fmt:message key="general.username"/>">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="j_password"><fmt:message
				key="general.password" /></label>
		<div class="controls">
			<input type="password" name="j_password"
				placeholder="<fmt:message key="general.password"/>">
		</div>
	</div>
	<div class="control-group">
		<button type="submit" class="btn">
			<fmt:message key="general.login" />
		</button>
	</div>
</form>

<commons:footer />