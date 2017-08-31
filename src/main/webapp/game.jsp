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

    <div class="wrapper">
        <div>
            <p>Игрок: ${user.name}</p>
            <p>Попыток сделано: ${game.stageCount}</p>
            <p>"1Б" означает, что одна цифра угадана точно.</p>
            <p>"2К" означает, что угаданы две цифры, но они находятся не на своем месте.</p>
            <p>Не запутайтесь. Помните, что цифры загаданного числа не повторяются!</p>
            <p>(Тест) число: ${game.number}</p>
        </div>
        <div>
            <c:if test="${game.stageCount > 0}">
                <c:import url="stage.jsp">
                    <c:param name="stage_number" value="Попытка №"/>
                    <c:param name="attempt" value="Число"/>
                    <c:param name="legend" value="Легенда"/>
                </c:import>
            </c:if>
            <c:forEach var="stage" items="${game.stageList}" varStatus="current">
                <div>
                <c:import url="stage.jsp">
                    <c:param name="stage_number" value="${stage.attemptCount}"/>
                    <c:param name="attempt" value="${stage.attempt}"/>
                    <c:param name="legend" value="${stage.legend}"/>
                </c:import>
                </div>
            </c:forEach>
        </div>

        <c:choose>
            <c:when test="${game.over}">
                <div>
                    <p>Победа! Вы отгадали число за ${game.stageCount} попыток!</p>
                    <p><a href="end_game">К таблице результатов</a></p>
                </div>
            </c:when>
            <c:otherwise>
                <div>
                    <c:import url="attempt.jsp">
                        <c:param name="digit_1" value="${requestScope.digit_1}"/>
                        <c:param name="digit_2" value="${requestScope.digit_2}"/>
                        <c:param name="digit_3" value="${requestScope.digit_3}"/>
                        <c:param name="digit_4" value="${requestScope.digit_4}"/>
                    </c:import>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>
