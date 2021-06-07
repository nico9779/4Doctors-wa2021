<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Home</title>
    <c:import url="/jsp/head.jsp"/>
</head>


<body>
<c:import url="/jsp/header.jsp"/>
<div id="site">
<c:import url="/jsp/doctor-nav.jsp"/><!--
    --><section>
<h1>List of your followed Patients</h1>
<br>
<!-- display the list of past medical examinations, if any -->
<c:if test='${not empty patientsList}'>
    <table>
        <thead>
        <tr>
            <td>CF</td><td>Name</td><td>Surname</td><td>email</td><td>Birthday</td>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="patient" items="${patientsList}">
            <tr>
                <td><c:out value="${patient.getCf()}"/></td>
                <td><c:out value="${patient.getName()}"/></td>
                <td><c:out value="${patient.getSurname()}"/></td>
                <td><c:out value="${patient.getEmail()}"/></td>
                <td><c:out value="${patient.getBirthday()}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<br>
<h3>Follow a new patient</h3>
<form method="POST" action="<c:url value="/add-my-patient"/>">
    <input name="patient_cf" type="text" placeholder="patient's fiscal code"/><br>
    <button type="submit">Add</button><br>
</form>
    </section><!--
--></div>
<c:import url="/jsp/footer.jsp"/>
</body>
</html>