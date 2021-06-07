<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Verify your account</title>
        <c:import url="/jsp/head.jsp"/>
        <link type="text/css" rel="stylesheet" href="<c:url value="/css/verify-style.css"/>">
        <script src="<c:url value="/js/login-registration-centralizer.js"/>"></script>
    </head>
    <body>
        <!-- display error/message -->
        <c:if test='${not empty message}'>
            <c:import url="/jsp/include/error-message.jsp"/>
        </c:if>

        <c:choose>
            <c:when test="${not empty sessionScope.verification_code and not empty sessionScope.patient}">
                <script>
                    sessionStorage.setItem("verification_code", "${sessionScope.verification_code}");
                </script>

                <div class="verify_box white-box">
                    <img src="<c:url value="/media/logo.png"/>" alt="Logo 4Doctors" title="Logo 4Doctors">
                    <br><br>
                    <p>We already sent a verification code to your email.</p>
                    <p>Please enter the code in the field.</p>

                    <form id="verification_form" method="POST" action="<c:url value="/user/verify"/>">
                        <input class="verify_code" id="code" type="text" name="code"/>
                        <div class="error"></div>
                        <input name="submit" type="submit" value="Verify">
                    </form>
                </div>
                <script src="${pageContext.request.contextPath}/js/verify.js"></script>
            </c:when>
            <c:otherwise>
                <div class="verify_box white-box">
                    <p class="verify_message">An error occurred while processing the request. You will redirected to the registration page in 5 seconds.</p>
                    <script>
                        setTimeout(function(){
                            window.location.href = "${pageContext.request.contextPath}/jsp/patient_registration.jsp";
                        }, 5000);
                    </script>
                </div>
            </c:otherwise>
        </c:choose>
    </body>
</html>