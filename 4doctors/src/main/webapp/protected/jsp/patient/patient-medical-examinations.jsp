<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <script type="text/javascript" src="<c:url value="/js/patient-medical-examinations.js"/>">
    </script>

	<head>
		<title>Medical Examination</title>
        <c:import url="/jsp/head.jsp"/>
        <link type="text/css" rel="stylesheet" href="<c:url value="/css/patient-medical-examination.css"/>">
	</head>

    <body>
        <c:import url="/jsp/header.jsp"/>
        <div id="site">
            <c:import url="/jsp/patient-nav.jsp"/><!--
            --><section>
                <h1>Examinations</h1>
                <br>
                <div id="reservation">
                    <div id="form-box" class="white-box">
                        <h2>New Reservation</h2>
                        <form action="javascript:void(0);">
                            <label for="patientDoctor">Select Doctor:</label>
                            <select name = "patientDoctor" id = "patientDoctor">
                                <c:forEach var = "patientDoctor" items="${patientDoctors}" >
                                    <option value ="${patientDoctor.cf}">${patientDoctor.surname} ${patientDoctor.name}</option>
                                </c:forEach>
                            </select><br/>

                            <label>Select Date:</label>
                            <input name="dateselect" type="date" id="dateselect"/><br/>
                            <div class="error"></div>

                            <label for="timeToSelect">Select Time:</label>
                            <select name = "timeToSelect" id = "timeToSelect">
                                <c:forEach var = "timeToSelect" items="${timeSelection}" >
                                    <option ng-disabled="${timeToSelect.booked}" value ="${timeToSelect.hour}:${timeToSelect.min}">${timeToSelect.hour}:${timeToSelect.min}</option>
                                </c:forEach>
                            </select><br/>
                            <div class="error"></div>

                            <button type="reset">Cancel</button>
                            <button onclick="addMedEx()" style="float: right">Confirm</button><br/>

                        </form>
                    </div>

                    <!-- display the list of FUTURE medical examinations -->
                        <table id="future-exams">
                            <caption class="h3">Future Examinations</caption>
                            <thead>
                            <tr>
                                <th>Date</th><th>Time</th><th>Doctor</th><th>Actions</th>
                            </tr>
                            </thead>

                            <tbody>
                                <c:choose>
                                    <c:when test='${not empty futureExaminationsList && !message.error}'>
                                        <c:forEach var="examination" items="${futureExaminationsList}">
                                            <tr>
                                                <td><c:out value="${examination.date}"/></td>
                                                <td><c:out value="${examination.time}"/></td>
                                                <td data-attr="${examination.doctor_cf}">
                                                    <c:forEach var = "patientDoctor" items="${patientDoctors}" >
                                                        <c:if test="${patientDoctor.cf == examination.doctor_cf}">
                                                            <p>${patientDoctor.surname} ${patientDoctor.name}</p>
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                                <td><button id="deletemedex" onclick="deleteMedEx(this)">Cancel</button></td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr id="notbooked"><td>No examinations booked.</td><td></td><td></td></tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>

                </div>

                <hr/>

                <!-- display the list of PAST medical examinations -->
                <div>
                    <c:if test='${not empty pastExaminationsList && !message.error}'>
                        <table>
                            <caption class="h3">Past Examinations</caption>
                            <thead>
                            <tr>
                                <th>Doctor</th><th>Date</th><th>Time</th><th>Outcome</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="examination" items="${pastExaminationsList}">
                                <tr>
                                    <td value="${examination.doctor_cf}">
                                        <c:forEach var = "patientDoctor" items="${patientDoctors}" >
                                            <c:if test="${patientDoctor.cf == examination.doctor_cf}">
                                                <p>${patientDoctor.surname} ${patientDoctor.name}</p>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td><c:out value="${examination.date}"/></td>
                                    <td><c:out value="${examination.time}"/></td>
                                    <td><button id="outcome" value="${examination.outcome}" onclick="openPopup(this)">Details</button></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>

                <!-- The Modal -->
                <div id="popupModal" class="modal">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h2>Examination Outcome</h2>
                        <span class="close" onclick="closePopup()">&times;</span>
                      </div>
                      <div class="modal-body">
                        <p id="outcomeText"></p>
                      </div>
                    </div>
                </div>
            </section>
        </div>

        <c:import url="/jsp/footer.jsp"/>

    </body>
</html>