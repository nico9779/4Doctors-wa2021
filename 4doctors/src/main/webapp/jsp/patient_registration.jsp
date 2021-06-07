<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Sign up</title>
    <c:import url="/jsp/head.jsp"/>
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/login-registration-style.css"/>">
    <style>
       .form-box {
           margin: 15px auto;
       }
    </style>
	<script src="<c:url value="/js/login-registration-centralizer.js"/>"></script>
</head>
<body>
    <!-- display error/message -->
    <c:if test='${not empty message}'>
        <c:import url="/jsp/include/error-message.jsp"/>
    </c:if>
	<div class="form-box white-box">
		<img src="<c:url value="/media/logo.png"/>" alt="Logo 4Doctors" title="Logo 4Doctors">
		<h3>Registration</h3>
		<form id="registration_form" method="POST" action="<c:url value="/patient"/>">
			<input id="cf" name="cf" type="text" placeholder="Fiscal Code" required>
			<div class="error"></div>
			<input id="name" name="name" type="text" placeholder="Name" required>
			<div class="error"></div>
			<input id="surname" name="surname" type="text" placeholder="Surname" required>
			<div class="error"></div>
			<input id="email" name="email" type="email" placeholder="Email" required>
			<div class="error"></div>
			<label>Birthday</label>
			<input id="birthday" name="birthday" type="date" placeholder="Birth date" required>
			<div class="error"></div>
			<input id="birthplace" name="birthplace" type="text" placeholder="Place of birth" required>
			<div class="error"></div>
			<input id="address" name="address" type="text" placeholder="Residence Address" required>
			<div class="error"></div>
			<div>
				<label><input id="male" type="radio" name="gender" value="M" required> Male </label>
				<label><input id="female" type="radio" name="gender" value="F" required> Female</label>
				<div class="error"></div>
			</div>
			<input id="password" name="password" type="password" placeholder="Password" required>
			<div id="password_strength" class="badge badge-pill"></div>
			<div class="error"></div>
			<input id="retype_password" name="retype_password" type="password" placeholder="Retype Password" required>
			<div class="error"></div>
			<br>
			<input name="submit" type="submit" value="Create account">
		</form>
	</div>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.0.0/crypto-js.min.js" integrity="sha512-nOQuvD9nKirvxDdvQ9OMqe2dgapbPB7vYAMrzJihw5m+aNcf0dX53m6YxM4LgA9u8e9eg9QX+/+mPu8kCNpV2A==" crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/js/registration.js"></script>
</body>
</html>