<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Add new medicine</title>
        <c:import url="/jsp/head.jsp"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/admin-add-medicine-style.css"/>">
    </head>
    <body>
    <c:import url="/jsp/header.jsp"/>
    <div id="site">
        <c:import url="/jsp/admin-nav.jsp"/><!--
        --><section>
            <div class="form-box white-box">
                <h2>Add new medicine</h2>
                <form id="add_medicine_form" method="POST">
                    <input id="m_code" name="m_code" type="text" placeholder="Medicine Code" maxlength="16">
                    <div class="error"></div>
                    <input id="name" name="name" type="text" placeholder="Name" maxlength="30">
                    <div class="error"></div>
                    <div>
                        <label><input id="ETICI" type="radio" name="type" value="ETICI" required> ETICI </label>
                        <label><input id="OTC" type="radio" name="type" value="OTC" required> OTC </label>
                        <label><input id="SOP" type="radio" name="type" value="SOP" required> SOP </label>
                        <div class="error"></div>
                    </div>
                    <input id="prod" name="producer" type="text" placeholder="Producer" maxlength="30">
                    <div class="error"></div>
                    <input id="description" name="description" type="text" placeholder="Description" maxlength="200">
                    <div class="error"></div>
                    <br>
                    <input name="submit" type="submit" value="Add new medicine">
                    <div id="add_medicine_message"></div>
                </form>
            </div>
        </section>

    </div>

    <c:import url="/jsp/footer.jsp"/>
    <script src="${pageContext.request.contextPath}/js/admin-add-medicine.js"></script>
    </body>
</html>
