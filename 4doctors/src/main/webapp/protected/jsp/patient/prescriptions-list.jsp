<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <%-- HEAD --%>
    <head>
        <title>Prescriptions List</title>
        <c:import url="/jsp/head.jsp"/>
        <link type="text/css" rel="stylesheet" href="<c:url value="/css/main-style.css"/>">
    </head>

    <%-- BODY --%>
    <body>

        <c:import url="/jsp/header.jsp"/>
        <div id="site">
            <c:import url="/jsp/patient-nav.jsp"/><!--
            --><section>
                <c:if test='${not empty cf}'>
                    <h1 class="welcome-title">List of all prescriptions of </h1>
                </c:if>


                <!-- display the list of all prescriptions, if any -->
                <c:if test='${not empty prescriptionsList}'>
                    <h3>Prescriptions</h3>
                    <table>
                        <thead>
                        <tr>
                            <td>Id</td><td>Doctor</td><td>Patient</td><td>Date</td><td>Description</td><td>Duration</td><td>Type</td><td>Status</td>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="prescription" items="${prescriptionsList}">
                            <!-- print all the rows -->
                            <tr>
                                <td><c:out value="${prescription.getId()}"/></td>
                                <td><c:out value="${prescription.getDoctor()}"/></td>
                                <td><c:out value="${prescription.getPatient()}"/></td>
                                <td><c:out value="${prescription.getDate()}"/></td>
                                <td><c:out value="${prescription.getDescription()}"/></td>
                                <td><c:out value="${prescription.getDuration()}"/></td>
                                <td><c:out value="${prescription.getType()}"/></td>
                                <td><c:out value="${prescription.getStatus()}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test='${empty prescriptionsList}'>
                    <h3>Prescriptions</h3>
                    <p>No prescriptions already present</p>
                </c:if>
                </section>
        </div>
        <script>var cf="${cf}"</script>
        <script>var role="${role}"</script>
        <script>var contexPath="${pageContext.request.contextPath}"</script>
        <script src="${pageContext.request.contextPath}/js/getUserName.js"></script>
        <c:import url="/jsp/footer.jsp"/>
    </body>
</html>