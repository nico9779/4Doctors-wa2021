<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Admin Login</title>
		<c:import url="/jsp/head.jsp"/>
		<link type="text/css" rel="stylesheet" href="<c:url value="/css/login-registration-style.css"/>">
		<script src="<c:url value="/js/login-registration-centralizer.js"/>"></script>
	</head>
	<body>

	<!-- display error/message -->
	<c:if test='${not empty message}'>
		<c:import url="/jsp/include/error-message.jsp"/>
	</c:if>
		<div class="form-box white-box">
			<img src="<c:url value="/media/logo.png"/>" alt="Logo 4Doctors" title="Logo 4Doctors">
			<h2>Admin Login</h2>
			<form id="login_form" method="POST" action="<c:url value="/admin/login"/>">
				<input id="user" name="username" type="text" placeholder="Username" required><br>
				<div class="error"></div>
				<input id="key" name="password" type="password" placeholder="Password" required><br>
				<div class="error"></div>
				<input name="submit" type="submit" value="login">
			</form>
		</div>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.0.0/crypto-js.min.js" integrity="sha512-nOQuvD9nKirvxDdvQ9OMqe2dgapbPB7vYAMrzJihw5m+aNcf0dX53m6YxM4LgA9u8e9eg9QX+/+mPu8kCNpV2A==" crossorigin="anonymous"></script>
		<script src="${pageContext.request.contextPath}/js/admin-login.js"></script>
	</body>
</html>