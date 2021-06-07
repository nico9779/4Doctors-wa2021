<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Add new doctor</title>
        <c:import url="/jsp/head.jsp"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/admin-add-doctor-style.css"/>">
    </head>
    <body>
    <c:import url="/jsp/header.jsp"/>
    <div id="site">
        <c:import url="/jsp/admin-nav.jsp"/><!--
        --><section>
            <div class="form-box white-box">
                <h2>Add new doctor</h2>
                <form id="add_doctor_form" method="POST">
                    <input id="cf" name="cf" type="text" placeholder="Fiscal Code" required >
                    <div class="error"></div>
                    <input id="name" name="name" type="text" placeholder="Name" required pattern="[A-Za-z ]+">
                    <div class="error"></div>
                    <input id="surname" name="surname" type="text" placeholder="Surname" required pattern="[A-Za-z ]+">
                    <div class="error"></div>
                    <input id="email" name="email" type="email" placeholder="Email" required pattern=".*@.*\..+">
                    <div class="error"></div>
                    <label>Birthday</label>
                    <input id="birthday" name="birthday" type="date" placeholder="Birth date" required>
                    <div class="error"></div>
                    <input id="birthplace" name="birthplace" type="text" placeholder="Place of birth" required pattern="[\w/ ]+">
                    <div class="error"></div>
                    <input id="address" name="address" type="text" placeholder="Residence Address" required pattern="[\w/ ]+">
                    <div class="error"></div>
                    <input id="asl_code" name="asl_code" type="text" placeholder="ASL Code" required pattern="[A-Z0-9]+">
                    <div class="error"></div>
                    <div>
                        <label><input id="male" type="radio" name="gender" value="M" required> Male </label>
                        <label><input id="female" type="radio" name="gender" value="F" required> Female</label>
                        <div class="error"></div>
                    </div>
                    <input id="password" name="password" type="password" placeholder="Password" required pattern=".{6,}">
                    <div id="password_strength" class="badge badge-pill"></div>
                    <div class="error"></div>
                    <input id="retype_password" name="retype_password" type="password" placeholder="Retype Password" required pattern=".{6,}">
                    <div class="error"></div>
                    <br>
                    <input name="submit" type="submit" value="Add new doctor">
                    <div id="add_doctor_message"></div>
                </form>
            </div>
        </section>

    </div>

    <c:import url="/jsp/footer.jsp"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.0.0/crypto-js.min.js" integrity="sha512-nOQuvD9nKirvxDdvQ9OMqe2dgapbPB7vYAMrzJihw5m+aNcf0dX53m6YxM4LgA9u8e9eg9QX+/+mPu8kCNpV2A==" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/admin-add-doctor.js"></script>
    </body>
</html>
