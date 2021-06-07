<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="overlay">
    <div id="error-message-box">
        <c:choose>
            <c:when test="${message.error}">
                <h1 class="error">ERROR</h1>
                <br>
                <p>error code: <c:out value="${message.errorCode}"/></p>
                <p>message: <c:out value="${message.message}"/></p>
                <p>details: <c:out value="${message.errorDetails}"/></p>
                <br>
                <p>Please contact the customer support if the problem persists</p>
            </c:when>
            <c:otherwise>
                <h1 class="message">INFO</h1>
                <br>
                <p><c:out value="${message.message}"/></p>
            </c:otherwise>
        </c:choose>
        <button>OK</button>
    </div>
</div>