<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Home</title>
    <c:import url="/jsp/head.jsp"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/profile-style.css"/>">
  </head>

  <body>
  <c:import url="/jsp/header.jsp"/>
  <div id="site">
    <c:import url="/jsp/doctor-nav.jsp"/><!--
    --><section>
      <div class="personal-info">
        <h1>Personal Information</h1>
        <br>
        <img class="roundpic" src="${pageContext.request.contextPath}/media/image-profile.png" alt="image-profile" />
        <br>
        <br>
        <!-- display the personal information of the logged doctor, if any -->
        <c:if test='${not empty personalInfo}'>
          <h3>CF</h3>
          <div class="pinfo-block">
            <p><c:out value="${personalInfo.getCf()}"/></p>
          </div>
          <br>
          <h3>Name</h3>
          <div class="pinfo-block">
            <p><c:out value="${personalInfo.getName()}"/></p>
          </div>
          <br>
          <h3>Surname</h3>
          <div class="pinfo-block">
            <p><c:out value="${personalInfo.getSurname()}"/></p>
          </div>
          <br>
          <h3>gender</h3>
          <div class="pinfo-block">
            <p><c:out value="${personalInfo.getGender()}"/></p>
          </div>
          <br>
          <h3>Email</h3>
          <div class="pinfo-block">
            <p><c:out value="${personalInfo.getEmail()}"/></p>
            <a class="button" href="${pageContext.request.contextPath}/protected/jsp/doctor/doctor-change-mail.jsp">Change</a>
          </div>
          <br>
          <h3>Birthday</h3>
          <div class="pinfo-block">
            <p><c:out value="${personalInfo.getBirthday()}"/></p>
          </div>
          <br>
          <h3>Birthplace</h3>
          <div class="pinfo-block">
            <p><c:out value="${personalInfo.getBirthplace()}"/></p>
          </div>
          <br>
          <h3>Address</h3>
          <div class="pinfo-block">
            <p><c:out value="${personalInfo.getAddress()}"/></p>
          </div>
        </c:if>
        <br><br>
        <a class="button" href="${pageContext.request.contextPath}/protected/jsp/doctor/doctor-change-password.jsp">Change Password</a>
      </div>
    </section>
  </div>
  <c:import url="/jsp/footer.jsp"/>
  </body>
</html>
