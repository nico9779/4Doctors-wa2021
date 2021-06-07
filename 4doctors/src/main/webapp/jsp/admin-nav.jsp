<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav>
    <img class="desktop-only" src="<c:url value="/media/logo-white.png"/>" alt="Logo 4Doctors" title="Logo 4Doctors">
    <a href="${pageContext.request.contextPath}/protected/jsp/admin/admin-homepage.jsp">Home</a>
    <a href="${pageContext.request.contextPath}/protected/jsp/admin/admin-add-doctor.jsp">Add new doctor</a>
    <a href="${pageContext.request.contextPath}/protected/jsp/admin/admin-add-medicine.jsp">Add new medicine</a>
    <a href="${pageContext.request.contextPath}/user/logout">Log out</a>
</nav>