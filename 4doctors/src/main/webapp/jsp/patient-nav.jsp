<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav>
    <img class="desktop-only" src="<c:url value="/media/logo-white.png"/>" alt="Logo 4Doctors" title="Logo 4Doctors">
    <a href="${pageContext.request.contextPath}/protected/jsp/patient/patient-homepage.jsp">Home</a>
    <a href="${pageContext.request.contextPath}/prescriptions?type=prescriptions">Prescriptions</a>
    <a href="${pageContext.request.contextPath}/patient-medical-examinations?type=Examinations">Examinations</a>
    <a href="${pageContext.request.contextPath}/profile?type=Profile">Profile</a>
    <a href="${pageContext.request.contextPath}/user/logout">Log out</a>
</nav>