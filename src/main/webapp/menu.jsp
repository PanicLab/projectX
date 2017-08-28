<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Бык-корова</title>
    <meta charset="UTF-8"/>
</head>
<body>
    Это страничка игры.
    Профиль: ${userName}

    <fieldset>
        <legend>Таблица результатов</legend>
        <table cellpadding="2" cellspacing="2">
            <tr>
                <th>Место</th>
                <th>Имя</th>
                <th>Ср. результат</th>
                <th>Лучший результат</th>
                <th>Последний результат</th>
            </tr>
            <c:forEach var="user" items="${requestScope.userList}" begin="0" varStatus="current">
                <tr>
                    <td>
                        <c:out value="${current.index + 1}"/>
                    </td>
                    <td>
                        <c:out value="${user.name}"/>
                    </td>
                    <td>
                        <fmt:formatNumber
                                type="number"
                                minFractionDigits="2"
                                maxFractionDigits="2"
                                value="${user.averageResult}"/>
<%--                        <c:out value="${user.averageResult}"/>--%>
                    </td>
                    <td>
                        <c:out value="${user.bestResult}"/>
                    </td>
                    <td>
                        <c:out value="${user.lastResult}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </fieldset>
</body>
</html>
