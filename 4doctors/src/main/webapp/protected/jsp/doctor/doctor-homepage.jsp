<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <%-- HEAD --%>
    <head>
        <title>Homepage</title>
        <c:import url="/jsp/head.jsp"/>
        <link type="text/css" rel="stylesheet" href="<c:url value="/css/homepage-style.css"/>">
        <style>
            #menu-button-container a:nth-child(1) div {
                background-image: url('${pageContext.request.contextPath}/media/patients-link.jpg');
            }
        </style>
    </head>
    <%-- BODY --%>
    <body>
        <c:import url="/jsp/header.jsp"/>
        <div id="site">
            <section>
                <br>
                <c:if test='${not empty cf}'>
                    <h1 class="welcome-title">Welcome, doctor </h1>
                </c:if>
                <a id="log-out-button" class="button button-2" href="${pageContext.request.contextPath}/user/logout">log out</a>
                <div id="menu-button-container">
                    <a href="${pageContext.request.contextPath}/list-my-patients">
                         <div class="white-box">
                            <h3>patients</h3>
                        </div>
                    </a>
                    <a href="${pageContext.request.contextPath}/prescription-manager">
                        <div class="white-box">
                            <h3>prescriptions</h3>
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