<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Prescription Manager</title>
		<c:import url="/jsp/head.jsp"/>
		<link type="text/css" rel="stylesheet" href="<c:url value="/css/prescription-manager-style.css"/>">
	</head>

    <body>
        <c:import url="/jsp/header.jsp"/>
        <div id="site">
            <c:import url="/jsp/doctor-nav.jsp"/><!--
            --><section>
                <h1>Prescriptions</h1>
                <br>
                <!-- display the list of found prescriptions if any -->
                <div>
                    <h3 class="prescription-header">Pending prescriptions</h3>
                    <c:if test='${not empty pending}'>
                    <table>
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Patient</th>
                                <th>Prescription</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="prescription" items="${pending}">
                            <tr data-id="<c:out value="${prescription.id}"/>">
                                <td><c:out value="${prescription.date}"/></td>
                                <td><c:out value="${prescription.patient}"/></td>
                                <td><c:out value="${prescription.description}"/></td>
                                <td>
                                    <form method="POST" action="<c:url value="prescription-update"/>">
                                        <input type="submit" value="Approve">
                                        <input type="hidden" name="id" value="<c:out value="${prescription.id}"/>">
                                        <input type="hidden" name="status" value="APPROVED">
                                    </form>
                                    <form method="POST" action="<c:url value="prescription-update"/>">
                                        <input type="submit" value="Reject">
                                        <input type="hidden" name="id" value="<c:out value="${prescription.id}"/>">
                                        <input type="hidden" name="status" value="REJECTED">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    </c:if>
                    <c:if test='${empty pending}'>
                        <div>
                            <p>There are not pending prescription requests</p>
                        </div>
                    </c:if>
                </div>
                <br>
                <div>
                    <h3 class="prescription-header">Past prescriptions</h3>
                <c:if test='${not empty past}'>
                    <table>
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Patient</th>
                                <th>Prescription</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="prescription" items="${past}">
                            <tr>
                                <td><c:out value="${prescription.date}"/></td>
                                <td><c:out value="${prescription.patient}"/></td>
                                <td><c:out value="${prescription.description}"/></td>
                                <td class="<c:out value="${prescription.status}"/>"><c:out value="${prescription.status}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                </div>
            </section><!--
        --></div>
        <c:import url="/jsp/footer.jsp"/>
	</body>
</html>