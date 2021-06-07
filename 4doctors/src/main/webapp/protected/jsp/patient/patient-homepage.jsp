<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <%-- HEAD --%>
    <head>
        <title>Homepage</title>
        <c:import url="/jsp/head.jsp"/>
        <link type="text/css" rel="stylesheet" href="<c:url value="/css/homepage-style.css"/>">
    </head>
    <%-- BODY --%>
    <body>
        <c:import url="/jsp/header.jsp"/>
        <div id="site">
            <section>
                <c:if test='${not empty cf}'>
                    <h2 class="welcome-title">Welcome, </h2>
                </c:if>
                <a id="log-out-button" class="button button-2" href="${pageContext.request.contextPath}/user/logout">log out</a>
                <div id="menu-button-container">
                    <a href="${pageContext.request.contextPath}/prescriptions?type=prescriptions">
                        <div class="white-box">
                            <h3>prescriptions</h3>
                        </div>
                    </a>
                    <a href="${pageContext.request.contextPath}/patient-medical-examinations?type=Examinations">
                         <div class="white-box">
                            <h3>examinations</h3>
                        </div>
                    </a>
                    <a href="${pageContext.request.contextPath}/profile?type=Profile">
                        <div class="white-box">
                            <h3>profile</h3>
                        </div>
                    </a>
                </div>
             </section>
        </div>
        <script>var cf="${cf}"</script>
        <script>var role="${role}"</script>
        <script>var contexPath="${pageContext.request.contextPath}"</script>
        <script src="${pageContext.request.contextPath}/js/getUserName.js"></script>
        <c:import url="/jsp/footer.jsp"/>
    </body>
</html>
