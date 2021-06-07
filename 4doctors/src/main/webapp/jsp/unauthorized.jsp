<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>401 Unauthorized</title>
        <c:import url="/jsp/head.jsp"/>
        <link type="text/css" rel="stylesheet" href="<c:url value="/css/unauthorized-style.css"/>">
        <script src="<c:url value="/js/login-registration-centralizer.js"/>"></script>
    </head>
    <body>
    <img id="unauthorized_img" src="<c:url value="/media/401-unauthorized-image.png"/>" alt="401 Unauthorized" title="401 Unauthorized">
    <c:choose>
        <c:when test='${sessionScope.role.equals("patient")}'>
            <a href="${pageContext.request.contextPath}/protected/jsp/patient/patient-homepage.jsp"><button type="button" class="back_button">back to the homepage</button></a>
        </c:when>
        <c:when test='${sessionScope.role.equals("doctor")}'>
            <a href="${pageContext.request.contextPath}/protected/jsp/doctor/doctor-homepage.jsp"><button type="button" class="back_button">back to the homepage</button></a>
        </c:when>
        <c:when test='${sessionScope.role.equals("admin")}'>
            <a href="${pageContext.request.contextPath}/protected/jsp/admin/admin-homepage.jsp"><button type="button" class="back_button">back to the homepage</button></a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/jsp/login.jsp"><button type="button" class="back_button">back to the login</button></a>
        </c:otherwise>
    </c:choose>
    <a id="credits" href="https://storyset.com/web" target="_blank">Illustration by Freepik Storyset</a>
    </body>
</html>
