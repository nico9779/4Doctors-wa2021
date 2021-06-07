<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <%-- HEAD --%>
    <head>
        <title>Prescriptions</title>
        <c:import url="/jsp/head.jsp"/>
        <link type="text/css" rel="stylesheet" href="<c:url value="/css/prescription-patient-style.css"/>">
    </head>
    <%-- BODY --%>
    <body>
        <c:import url="/jsp/header.jsp"/>
        <div id="site">
            <c:import url="/jsp/patient-nav.jsp"/><!--
            --><section>
                <c:if test='${not empty cf}'>
                <h1>Prescriptions management page</h1>
                <br>
                </c:if>

                <%-- List the prescriptions --%>
                <h3>List of your prescriptions</h3>
                <form method="GET" action="<c:url value="/list-user-prescriptions"/>">
                    <input name="typeReq" type="submit" value="all" />
                    <input name="typeReq" type="submit" value="pending" />
                    <input name="typeReq" type="submit" value="rejected" />
                    <input name="typeReq" type="submit" value="approved" />
                </form>

                <%-- Request --%>
                <br>
                <h3>Prescription request</h3>
                <form method="POST" id="p_form" action="<c:url value="/prescription-request"/>">
                    <div>
                        <input class="req" name="doctor_email" type="text" placeholder="Doctor email" /><br>
                        <div class="error"></div>
                        <input class="req" name="description" type="text" placeholder="Description" /><br>
                        <div class="error"></div>
                        <label>Times of validity (max. 10)</label>
                        <input class="req" type="number" id="quantity" name="numeroprestazioni" min="1" max="10" /><br>
                        <div class="error"></div>
                        <div id="radioB">
                            <input class="req" type="radio" id="exam" name="type" value="ESAME" >
                            <label for="exam">Exam</label>
                            <input class="req" type="radio" id="medicine" name="type" value="FARMACO">
                            <label for="medicine">Medicine</label>
                        </div><br>
                        <div class="error"></div>
                        <div id="medicine_select">
                            <label>Choose a medicine</label>
                            <select class="reqM" name="codeM" id="med_select" ></select>
                        </div>
                        <div class="error"></div>
                        <div id="exam_select">
                             <label>Choose an exam</label>
                             <select class="reqE" name="codeE" id="ex_select" required></select>
                        </div>
                        <div class="error"></div>
                        <br>
                        <div id="quant">
                            <label>Quantity</label>
                            <input class="reqM" type="number" id="qnt" name="qnt" />
                        </div><br>
                        <div class="error"></div>
                    </div>
                    <div>
                        <button id="request" type="submit">Submit</button>
                        <button type="reset">Reset</button>
                    </div>

                </form>
                <br>

                <%-- Available medicines prescripted to the user --%>
                <div>
                <h3>Prescribed medicines</h3>
                    <button id="pres_med" value=<c:out value="${cf}"/> >List</button>
                    <br>
                    <div id="medicine_wrapper">
                         <table id="medicine_table">
                            <thead>
                                <tr>
                                    <th scope="col">Code</th>
                                    <th scope="col">Name</th>
                                    <th scope="col">Description</th>
                                    <th scope="col">Medicine class</th>
                                    <th scope="col">Producer</th>
                                </tr>
                            </thead>
                            <tbody></tbody>
                         </table>
                    </div>
                </div>
        </section>
        </div>
        <script>var cf="${cf}"</script>
        <script>var role="${role}"</script>
        <script>var contexPath="${pageContext.request.contextPath}"</script>
        <script type="text/javascript" src="<c:url value="/js/prescription-home.js"/>"></script>
        <c:import url="/jsp/footer.jsp"/>
    </body>

</html>