<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Бык-корова</title>
</head>
<body>а

    <c:choose>
        <c:when test="${param.action == 'login'}">
            <c:import url="login_form.jsp">
                <c:param name="message" value="Пожалуйста, введите необходимые данные."/>
                <c:param name="on_click_button_action" value="Войти"/>
            </c:import>
        </c:when>
        <c:otherwise>
            <c:import url="login_form.jsp">
                <c:param name="message" value="Пожалуйста, зарегистрируйтесь."/>
                <c:param name="on_click_button_action" value="Зарегистрироваться."/>
            </c:import>
        </c:otherwise>
    </c:choose>

</body>
</html>
