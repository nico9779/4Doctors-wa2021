<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Medicine List</title>
        <c:import url="/jsp/head.jsp"/>
    </head>

    <body>
        <c:import url="/jsp/header.jsp"/>
        <div id="site">
            <c:import url="/jsp/patient-nav.jsp"/><!--
                --><section>
                    <h2>List of medicines actually available</h2>

                    <!-- display the message -->
                    <c:if test='${not empty message}'>
                        <c:import url="/jsp/include/show-message.jsp"/>
                    </c:if>

                    <!-- display the list of past medical examinations, if any -->
                    <c:if test='${not empty medicineList}'>
                    <h3>Medicines</h3>
                    <table>
                        <thead>
                        <tr>
                            <td>Code</td><td>Name</td><td>Class</td><td>Producer</td><td>Description</td>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="medicine" items="${medicineList}">
                            <tr>
                                <td><c:out value="${medicine.getCode()}"/></td>
                                <td><c:out value="${medicine.getName()}"/></td>
                                <td><c:out value="${medicine.getMedicine_class()}"/></td>
                                <td><c:out value="${medicine.getProducer()}"/></td>
                                <td><c:out value="${medicine.getDescription()}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    </c:if>
                    </section>
        </div>
        <c:import url="/jsp/footer.jsp"/>
    </body>
</html>
