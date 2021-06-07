<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Change Password</title>
    <c:import url="/jsp/head.jsp"/>
  </head>

  <body>
    <c:import url="/jsp/header.jsp"/>
    <div id="site">
      <c:import url="/jsp/patient-nav.jsp"/><!--
      --><section>
          <h1>Change Password</h1>

          <form id="change_password_form" method="POST" action="<c:url value="/change-password"/>">
              <input type="password" name="current" placeholder="Current password"><br>
              <div class="error"></div>
              <input id="pwd" type="password" name="new" placeholder="New password"><br>
              <div id="password_strength" class="badge badge-pill"></div>
              <div class="error"></div>
              <input type="password" name="confirm" placeholder="Confirm password"><br>
              <div class="error"></div>
              <input type="submit" value="Change Password">
          </form>
          </section>
    </div>
    <c:import url="/jsp/footer.jsp"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.0.0/crypto-js.min.js" integrity="sha512-nOQuvD9nKirvxDdvQ9OMqe2dgapbPB7vYAMrzJihw5m+aNcf0dX53m6YxM4LgA9u8e9eg9QX+/+mPu8kCNpV2A==" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/change-password-check.js"></script>
  </body>


