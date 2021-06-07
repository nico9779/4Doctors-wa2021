<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Login</title>
	<c:import url="/jsp/head.jsp"/>
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/login-registration-style.css"/>">
	<script src="<c:url value="/js/login-registration-centralizer.js"/>"></script>
</head>
<body>
	<div class="hero-text">
		<img src="<c:url value="/media/logo.png"/>" alt="Logo 4Doctors" title="Logo 4Doctors">
	</div>

    <!-- display error/message -->
    <c:if test='${not empty message}'>
        <c:import url="/jsp/include/error-message.jsp"/>
    </c:if>
	<div class="form-box white-box">
		<img src="<c:url value="/media/logo.png"/>" alt="Logo 4Doctors" title="Logo 4Doctors">
		<form id="login_form" method="POST" action="<c:url value="/user/login"/>">
			<input id="user" name="cf" type="text" placeholder="Username" required><br>
			<div class="error"></div>
			<input id="key" name="password" type="password" placeholder="Password" required><br>
			<div class="error"></div>
			<div>
				<label><input id="patient" type="radio" name="role" value="patient" required> Patient</label>
				<label><input id="doctor" type="radio" name="role" value="doctor" required> Doctor</label>
				<div class="error"></div>
			</div>
			<input name="submit" type="submit" value="login">
		</form>
		<br>
		<p>Don't you have an account yet? <a href="${pageContext.request.contextPath}/jsp/patient_registration.jsp">Sign up</a></p>
	</div>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.0.0/crypto-js.min.js" integrity="sha512-nOQuvD9nKirvxDdvQ9OMqe2dgapbPB7vYAMrzJihw5m+aNcf0dX53m6YxM4LgA9u8e9eg9QX+/+mPu8kCNpV2A==" crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
</html>