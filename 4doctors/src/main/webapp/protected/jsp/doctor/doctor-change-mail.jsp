<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Change E-mail</title>
    <c:import url="/jsp/head.jsp"/>
</head>

<body>
<c:import url="/jsp/header.jsp"/>
<div id="site">
    <c:import url="/jsp/doctor-nav.jsp"/><!--
        --><section>
    <h2>Change E-mail</h2>
    <form id="update_email_form" method="POST" action="<c:url value="/change-email"/>">
        <input id="email-current" type="text" name="current" placeholder="Current e-mail"><br>
        <div class="error"></div>
        <input id="email-new" type="text" name="new" placeholder="New e-mail"><br>
        <div class="error"></div>
        <input type="submit" value="Change E-mail">
    </form>
</section>
</div>
<c:import url="/jsp/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/change-mail-check.js"></script>
</body>
