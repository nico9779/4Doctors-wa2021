<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- display error/message -->
<c:if test='${not empty message}'>
    <c:import url="/jsp/include/error-message.jsp"/>
</c:if>

<header class="mobile-only">
    <div id="toggle-menu-button">
        <span></span>
        <span></span>
        <span></span>
    </div>
</header>