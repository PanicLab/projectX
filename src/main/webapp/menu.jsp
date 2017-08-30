<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css"/>" />
    <title>Бык-корова</title>
</head>
<body>

    Вперед, ${user.name}!

    <fieldset>
        <legend>Таблица результатов</legend>
        <table cellpadding="2" cellspacing="2">
            <caption>ЛУЧШИЕ ИЗ ЛУЧШИХ</caption>
            <tr>
                <th>Место</th>
                <th>Имя</th>
                <th>Ср. результат</th>
                <th>Лучший результат</th>
                <th>Последний результат</th>
                <th>Попыток</th>
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
                        <c:choose>
                            <c:when test="${empty user.averageResult || user.averageResult == 0}">
                                <c:out value="-"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:formatNumber
                                        type="number"
                                        minFractionDigits="2"
                                        maxFractionDigits="2"
                                        value="${user.averageResult}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${empty user.bestResult || user.bestResult == 0}">
                                <c:out value="-"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${user.bestResult}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${empty user.lastResult || user.lastResult == 0}">
                                <c:out value="-"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${user.lastResult}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${empty user.attemptsCount || user.attemptsCount == 0}">
                                <c:out value="-"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${user.attemptsCount}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </fieldset>
    <div class="simple_block">
        <p><a href="launcher">Начать игру</a>
    </div>
</body>
</html>
