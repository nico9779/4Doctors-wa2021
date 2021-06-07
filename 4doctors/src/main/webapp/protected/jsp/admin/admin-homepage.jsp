<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Homepage</title>
    <c:import url="/jsp/head.jsp"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/admin-homepage-style.css"/>">
  </head>
  <body>
    <c:import url="/jsp/header.jsp"/>
    <div id="site">
      <c:import url="/jsp/admin-nav.jsp"/><!--
      --><section>
            <c:if test='${not empty sessionScope.username}'>
              <h1>Welcome, <c:out value="${sessionScope.username}"/></h1>
            </c:if>
            <br>
            <h3 id="list_doctors_h3">List of all active doctors</h3>
            <div id="doctors_wrapper">
              <table id="doctors_table">
                <tr>
                  <th scope="col">Doctor</th>
                  <th scope="col">Name</th>
                  <th scope="col">Surname</th>
                  <th scope="col">Options</th>
                </tr>
              </table>
            </div>
            <br>
            <h3 id="list_patients_h3">List of all patients</h3>
            <div id="patients_wrapper">
              <table id="patients_table">
                <tr>
                  <th scope="col">Patient</th>
                  <th scope="col">Name</th>
                  <th scope="col">Surname</th>
                  <th scope="col">Options</th>
                </tr>
              </table>
            </div>
      </section><!--
    --></div>

    <c:import url="/jsp/footer.jsp"/>
    <script>var contextPath="${pageContext.request.contextPath}"</script>
    <script src="${pageContext.request.contextPath}/js/admin-homepage.js"></script>
  </body>
</html>
